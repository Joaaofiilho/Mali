package com.joaoferreira.mali.supermarket_list.viewmodels

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joaoferreira.domain.models.MarketItem
import com.joaoferreira.domain.usecases.*
import com.joaoferreira.domain.utils.Categories
import com.joaoferreira.domain.utils.FieldFailure
import com.joaoferreira.domain.utils.Fields
import com.joaoferreira.domain.utils.RemoteDatasourceFailure
import com.joaoferreira.mali.R
import com.joaoferreira.mali.utils.ScreenAlertType
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.*

data class SupermarketListUiState(
    val marketItems: List<MarketItem> = emptyList(),
    val loading: Boolean = false,
    val shouldShowBottomSheet: Boolean = false,
    val title: String = "",
    val quantity: Int = 1,
    val category: Categories? = null,
    val isErrorTitle: Boolean = false,
    val isFetchError: Boolean = false,
    val updatingItemIds: MutableList<String> = mutableListOf(),
    val deletedMarketItemErrorId: String? = null,
    val feedbackState: SupermarketListFeedbackState? = null,
)

data class SupermarketListFeedbackState(
    val type: ScreenAlertType = ScreenAlertType.FEEDBACK,
    @StringRes val message: Int = R.string.something_went_wrong,
    val stringArgs: List<Any> = listOf(),
)

class SupermarketListViewModel(
    private val createMarketItemUseCase: CreateMarketItemUseCase,
    private val getAllMarketItemsUseCase: GetAllMarketItemsUseCase,
    private val getByIdMarketItemUseCase: GetByIdMarketItemUseCase,
    private val updateMarketItemUseCase: UpdateMarketItemUseCase,
    private val deleteAllDoneMarketItemUseCase: DeleteAllDoneMarketItemUseCase,
    private val deleteByIdMarketItemUseCase: DeleteByIdMarketItemUseCase,
) : ViewModel() {

    val uiState = MutableStateFlow(SupermarketListUiState())

    //UI Management
    fun setShouldShowBottomSheet(shouldShowBottomSheet: Boolean) {
        uiState.update {
            uiState.value.copy(
                shouldShowBottomSheet = shouldShowBottomSheet,
            )
        }
        resetBottomSheet()
    }

    fun hideFeedback() {
        uiState.update {
            uiState.value.copy(
                feedbackState = null,
            )
        }
    }

    fun hideFetchError() {
        uiState.update {
            uiState.value.copy(
                isFetchError = false,
            )
        }
    }

    private fun resetBottomSheet() {
        uiState.update {
            uiState.value.copy(
                title = "",
                quantity = 1,
                category = null,
                isErrorTitle = false,
            )
        }
    }

    //Business logic management
    fun groupAndSortMarketItems(marketItems: List<MarketItem>): Map<Categories?, List<MarketItem>> =
        marketItems.sortedBy { it.title.lowercase(Locale.getDefault()) }.groupBy { it.category }
            .toSortedMap { c1, c2 ->
                if (c1 == null) {
                    return@toSortedMap 1
                }

                if (c2 == null) {
                    return@toSortedMap -1
                }

                c1.name.compareTo(c2.name)
            }

    fun setTitle(title: String) {
        uiState.update {
            uiState.value.copy(
                title = title
            )
        }
    }

    fun setQuantity(quantity: Int) {
        uiState.update {
            uiState.value.copy(
                quantity = quantity
            )
        }
    }

    fun setCategory(category: Categories?) {
        uiState.update {
            uiState.value.copy(
                category = category
            )
        }
    }

    fun removeDeletedMarketItemErrorId() {
        uiState.update {
            uiState.value.copy(
                deletedMarketItemErrorId = null,
            )
        }
    }

    fun createMarketItem(title: String, quantity: Int, category: Categories?) {
        viewModelScope.launch {
            uiState.update {
                uiState.value.copy(
                    loading = true
                )
            }
            val result =
                createMarketItemUseCase(CreateMarketItemUseCase.Params(title, quantity, category))

            result.onSuccess {
                setShouldShowBottomSheet(false)
            }.onFailure {
                when (it) {
                    is FieldFailure -> {
                        if (it.field == Fields.TITLE) {
                            uiState.update {
                                uiState.value.copy(
                                    isErrorTitle = true,
                                )
                            }
                        }
                    }
                }
            }

            uiState.update {
                uiState.value.copy(
                    loading = false
                )
            }
        }
    }

    fun getAllMarketItems() {
        viewModelScope.launch {
            getAllMarketItemsUseCase(NoParams())
                .onStart {
                    uiState.update {
                        uiState.value.copy(
                            loading = true
                        )
                    }
                }
                .catch {
                    uiState.update {
                        uiState.value.copy(
                            feedbackState = SupermarketListFeedbackState(
                                type = ScreenAlertType.ERROR,
                                message = R.string.fetch_items_error,
                            ),
                            isFetchError = true,
                        )
                    }
                }
                .collect { result ->
                    result
                        .onSuccess { marketItems ->
                            uiState.update {
                                uiState.value.copy(
                                    marketItems = marketItems,
                                    loading = false
                                )
                            }
                        }.onFailure {
                            uiState.update {
                                uiState.value.copy(
                                    loading = false,
                                    feedbackState = SupermarketListFeedbackState(
                                        type = ScreenAlertType.ERROR,
                                        message = R.string.something_went_wrong
                                    ),
                                    isFetchError = true,
                                )
                            }
                        }
                }
        }
    }

    fun updateMarketItem(marketItem: MarketItem) {
        viewModelScope.launch {
            if (!uiState.value.updatingItemIds.contains(marketItem.id)) {
                uiState.update {
                    uiState.value.copy(
                        updatingItemIds = mutableListOf<String>().apply {
                            addAll(uiState.value.updatingItemIds)
                            add(marketItem.id)
                        },
                    )
                }
            }

            val result = updateMarketItemUseCase(UpdateMarketItemUseCase.Params(marketItem))

            result.onSuccess {
                uiState.update {
                    uiState.value.copy(
                        updatingItemIds = mutableListOf<String>().apply {
                            addAll(uiState.value.updatingItemIds)
                            remove(marketItem.id)
                        },
                        feedbackState = SupermarketListFeedbackState(
                            type = ScreenAlertType.FEEDBACK,
                            message = R.string.item_updated_successfully,
                            stringArgs = listOf(marketItem.title)
                        )
                    )
                }
            }.onFailure { error ->
                uiState.update {
                    uiState.value.copy(
                        updatingItemIds = mutableListOf<String>().apply {
                            addAll(uiState.value.updatingItemIds)
                            remove(marketItem.id)
                        },
                        feedbackState = SupermarketListFeedbackState(
                            type = ScreenAlertType.ERROR,
                            message = if (error is RemoteDatasourceFailure) R.string.item_updated_remote_error else R.string.item_updated_error,
                            stringArgs = listOf(marketItem.title),
                        ),
                    )
                }
            }
        }
    }

    fun deleteMarketItem(marketItem: MarketItem) {
        val id = marketItem.id
        viewModelScope.launch {
            if (!uiState.value.updatingItemIds.contains(id)) {
                uiState.update {
                    uiState.value.copy(
                        updatingItemIds = mutableListOf<String>().apply {
                            addAll(uiState.value.updatingItemIds)
                            add(id)
                        },
                    )
                }
            }

            val result = deleteByIdMarketItemUseCase(DeleteByIdMarketItemUseCase.Params(id))

            result.onSuccess {
                uiState.update {
                    uiState.value.copy(
                        updatingItemIds = mutableListOf<String>().apply {
                            addAll(uiState.value.updatingItemIds)
                            remove(id)
                        },
                        feedbackState = SupermarketListFeedbackState(
                            type = ScreenAlertType.FEEDBACK,
                            message = R.string.item_deleted_successfully,
                            stringArgs = listOf(marketItem.title)
                        )
                    )
                }
            }.onFailure { error ->
                uiState.update {
                    uiState.value.copy(
                        updatingItemIds = mutableListOf<String>().apply {
                            addAll(uiState.value.updatingItemIds)
                            remove(id)
                        },
                        deletedMarketItemErrorId = id,
                        feedbackState = SupermarketListFeedbackState(
                            type = ScreenAlertType.ERROR,
                            message = if (error is RemoteDatasourceFailure) R.string.item_deleted_remote_error else R.string.item_deleted_error,
                            stringArgs = listOf(marketItem.title)
                        )
                    )
                }
            }
        }
    }
}
