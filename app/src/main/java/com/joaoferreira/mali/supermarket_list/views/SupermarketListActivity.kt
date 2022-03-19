package com.joaoferreira.mali.supermarket_list.views

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.joaoferreira.mali.R
import com.joaoferreira.mali.core.ui.components.EmptyLazyColumn
import com.joaoferreira.mali.core.ui.theme.MaliTheme
import com.joaoferreira.mali.supermarket_list.components.CreateMarketItemBottomSheet
import com.joaoferreira.mali.supermarket_list.components.ErrorContent
import com.joaoferreira.mali.supermarket_list.components.LoadingContent
import com.joaoferreira.mali.supermarket_list.components.SupermarketItem
import com.joaoferreira.mali.supermarket_list.viewmodels.SupermarketListUiState
import com.joaoferreira.mali.supermarket_list.viewmodels.SupermarketListViewModel
import com.joaoferreira.mali.utils.CategoryToString
import com.joaoferreira.mali.utils.ScreenAlertType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SupermarketListActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaliTheme {
                val viewModel: SupermarketListViewModel by remember { viewModel() }
                val supermarketListScreenState =
                    rememberSupermarketListScreenState(viewModel = viewModel)
                SupermarketListScreen(supermarketListScreenState)
            }
        }
    }
}

@Composable
fun SupermarketListScreen(screenState: SupermarketListScreenState) {
    val uiState by screenState.viewModel.uiState.collectAsState()

    LaunchedEffect(true) {
        screenState.viewModel.getAllMarketItems()
    }

    SupermarketListContent(
        screenState,
        uiState
    )
}

@OptIn(ExperimentalMaterialApi::class, androidx.compose.foundation.ExperimentalFoundationApi::class,
    androidx.compose.ui.ExperimentalComposeUiApi::class
)
@Composable
fun SupermarketListContent(
    screenState: SupermarketListScreenState,
    uiState: SupermarketListUiState
) {
    ModalBottomSheetLayout(
        sheetState = screenState.modalBottomSheetState,
        sheetContent = {
            CreateMarketItemBottomSheet(
                title = uiState.title,
                onTitleChange = { screenState.viewModel.setTitle(it) },
                quantity = uiState.quantity,
                onQuantityChange = { screenState.viewModel.setQuantity(it) },
                category = uiState.category,
                onCategoryChange = { screenState.viewModel.setCategory(it) },
                isErrorTitle = uiState.isErrorTitle,
                shouldOpenBottomSheet = uiState.shouldShowBottomSheet,
                onConfirmItem = { mTitle, mQuantity, mCategory ->
                    screenState.viewModel.createMarketItem(mTitle, mQuantity, mCategory)
                },
                onDismiss = {
                    screenState.viewModel.setShouldShowBottomSheet(false)
                }
            )
        },
        sheetElevation = 16.dp,
        sheetShape = RoundedCornerShape(8.dp),
    ) {
        Scaffold(
            scaffoldState = screenState.scaffoldState,
            topBar = {
                TopAppBar(elevation = 0.dp, backgroundColor = MaterialTheme.colors.background) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painterResource(R.drawable.ic_logo),
                            "",
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            },
            floatingActionButton = {
                AnimatedVisibility(visible = !uiState.isFetchError) {
                    FloatingActionButton(onClick = {
                        screenState.viewModel.setShouldShowBottomSheet(true)
                    }) {
                        Icon(Icons.Default.Add, "")
                    }
                }
            },
            snackbarHost = {
                SnackbarHost(hostState = it) { data ->
                    if (uiState.feedbackState?.type == ScreenAlertType.FEEDBACK) {
                        Snackbar(
                            snackbarData = data,
                            backgroundColor = MaterialTheme.colors.primary,
                            contentColor = MaterialTheme.colors.onPrimary,
                        )
                    } else {
                        Snackbar(
                            snackbarData = data,
                            backgroundColor = MaterialTheme.colors.error,
                            contentColor = MaterialTheme.colors.onError,
                        )
                    }
                }
            }
        ) {
            ErrorContent(
                hasError = uiState.isFetchError,
                errorContent = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.align(Alignment.Center)
                    ) {
                        Text(stringResource(R.string.fetch_items_error))
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedButton(
                            onClick = {
                                screenState.viewModel.getAllMarketItems()
                                screenState.viewModel.hideFetchError()
                            }
                        ) {
                            Text(stringResource(R.string.try_again))
                        }
                    }
                },
                modifier = Modifier.fillMaxSize()
            ) {
                LoadingContent(uiState.loading) {
                    val groupedMarketItems =
                        screenState.viewModel.groupAndSortMarketItems(uiState.marketItems)
                    EmptyLazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        isEmpty = uiState.marketItems.isEmpty() && !uiState.loading,
                        emptyContent = {
                            Text(
                                stringResource(R.string.empty_items),
                                textAlign = TextAlign.Justify,
                                modifier = Modifier
                                    .padding(horizontal = 32.dp)
                                    .align(Alignment.Center)
                            )
                        }
                    ) {
                        groupedMarketItems.forEach {
                            stickyHeader {
                                val category = it.key
                                Column {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .background(MaterialTheme.colors.background)
                                            .padding(vertical = 8.dp)
                                    ) {
                                        Spacer(modifier = Modifier.width(16.dp))
                                        Text(category?.let { CategoryToString(category) }
                                            ?: stringResource(R.string.category_none),
                                            style = MaterialTheme.typography.h2)
                                    }
                                    Spacer(modifier = Modifier.height(8.dp))
                                }
                            }

                            itemsIndexed(it.value) { index, marketItem ->
                                val dismissState: DismissState = key(marketItem.id) {
                                    rememberDismissState(
                                        confirmStateChange = { dismissValue ->
                                            if (dismissValue == DismissValue.DismissedToStart || dismissValue == DismissValue.DismissedToEnd) {
                                                screenState.viewModel.deleteMarketItem(marketItem)
                                            }
                                            true
                                        }
                                    )
                                }

                                if (uiState.deletedMarketItemErrorId == marketItem.id) {
                                    screenState.resetDismissState(dismissState)
                                    screenState.viewModel.removeDeletedMarketItemErrorId()
                                }

                                SupermarketItem(
                                    marketItem,
                                    loading = uiState.updatingItemIds.contains(marketItem.id),
                                    dismissState = dismissState,
                                    modifier = Modifier.padding(
                                        horizontal = 16.dp,
                                        vertical = 16.dp
                                    )
                                ) {
                                    screenState.viewModel.updateMarketItem(marketItem.copy(isDone = !marketItem.isDone))
                                }
                                if (index != it.value.lastIndex) {
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Row {
                                        Spacer(modifier = Modifier.width(16.dp))
                                        Divider(
                                            thickness = 1.dp,
                                            modifier = Modifier.fillMaxWidth(0.95f)
                                        )
                                        Spacer(modifier = Modifier.width(16.dp))
                                    }
                                    Spacer(modifier = Modifier.height(2.dp))
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    val message = uiState.feedbackState?.let {
        stringResource(
            uiState.feedbackState.message,
            *uiState.feedbackState.stringArgs.toTypedArray()
        )
    }
    LaunchedEffect(uiState.feedbackState) {
        message?.let {
            screenState.scaffoldState.snackbarHostState.showSnackbar(it)
            screenState.viewModel.hideFeedback()
        }
    }

    LaunchedEffect(uiState.shouldShowBottomSheet) {
        if (uiState.shouldShowBottomSheet) {
            screenState.modalBottomSheetState.show()
        } else {
            /*
            * There is a bug with the bottom sheet where it doesn't disappear when the keyboard is
            * open while it is hiding, so it needs to wait some time to start hiding.
            * It would never go to production, so I'm leaving this as is for this challenge only.
            * I probably would ask to change it for a Dialog.
            * */
            delay(1000)
            screenState.modalBottomSheetState.hide()
        }
    }
}

class SupermarketListScreenState @OptIn(ExperimentalMaterialApi::class) constructor(
    val scaffoldState: ScaffoldState,
    val modalBottomSheetState: ModalBottomSheetState,
    private val coroutineScope: CoroutineScope,
    val viewModel: SupermarketListViewModel,
) {

    @OptIn(ExperimentalMaterialApi::class)
    fun resetDismissState(dismissState: DismissState) {
        coroutineScope.launch {
            dismissState.reset()
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun rememberSupermarketListScreenState(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    modalBottomSheetState: ModalBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmStateChange = { false }
    ),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    viewModel: SupermarketListViewModel,
) = remember {
    SupermarketListScreenState(
        scaffoldState,
        modalBottomSheetState,
        coroutineScope,
        viewModel,
    )
}