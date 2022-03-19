package com.joaoferreira.mali.supermarket_list.components

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.joaoferreira.domain.models.MarketItem
import com.joaoferreira.domain.utils.Categories
import com.joaoferreira.mali.R
import com.joaoferreira.mali.core.ui.theme.MaliTheme

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CreateMarketItemBottomSheet(
    title: String,
    onTitleChange: (title: String) -> Unit,
    quantity: Int,
    onQuantityChange: (quantity: Int) -> Unit,
    category: Categories?,
    onCategoryChange: (category: Categories?) -> Unit,
    isErrorTitle: Boolean = false,
    shouldOpenBottomSheet: Boolean,
    onConfirmItem: (title: String, quantity: Int, category: Categories?) -> Unit,
    onDismiss: () -> Unit,
) {
    val categories = remember { Categories.values() }

    var shouldOpenDropdownMenu by remember { mutableStateOf(false) }
    var selectedIndexCategory by remember(shouldOpenBottomSheet) { mutableStateOf(-1) }

    var isEditingTitle by remember(shouldOpenBottomSheet) { mutableStateOf(false) }

    val focusManager = LocalFocusManager.current

    Column(modifier = Modifier.padding(8.dp)) {
        TextField(
            value = title,
            isError = isErrorTitle && !isEditingTitle,
            label = { Text(stringResource(R.string.title)) },
            onValueChange = {
                onTitleChange(it)
                isEditingTitle = true
            },
            modifier = Modifier.fillMaxWidth()
        )
        if (isErrorTitle && !isEditingTitle) {
            Text(
                stringResource(R.string.title_missing_error),
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.caption
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row {
            ExposedDropdownMenuBox(
                expanded = shouldOpenDropdownMenu,
                onExpandedChange = { shouldOpenDropdownMenu = !shouldOpenDropdownMenu },
                modifier = Modifier.weight(1f)
            ) {
                TextField(
                    value = if (selectedIndexCategory >= 0) categories[selectedIndexCategory].name else "",
                    onValueChange = {},
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = shouldOpenDropdownMenu
                        )
                    },
                    readOnly = true,
                    label = { Text(stringResource(R.string.category)) },
                    colors = ExposedDropdownMenuDefaults.textFieldColors()
                )
                ExposedDropdownMenu(
                    expanded = shouldOpenDropdownMenu,
                    onDismissRequest = { shouldOpenDropdownMenu = false }) {

                    Categories.values().forEachIndexed { index, category ->
                        DropdownMenuItem(onClick = {
                            shouldOpenDropdownMenu = false
                            selectedIndexCategory = index
                            onCategoryChange(categories[selectedIndexCategory])
                        }) {
                            Text(category.name)
                        }
                    }
                }
            }
            Row {
                QuantityContent(
                    quantity,
                    modifier = Modifier
                        .width(24.dp)
                        .align(Alignment.CenterVertically)
                )
                Spacer(modifier = Modifier.width(2.dp))
                Column {
                    Icon(
                        Icons.Default.ArrowDropDown,
                        "",
                        modifier = Modifier
                            .rotate(180f)
                            .size(32.dp)
                            .clickable {
                                onQuantityChange(quantity + 1)
                            }
                    )
                    Icon(
                        Icons.Default.ArrowDropDown,
                        "",
                        tint = if (quantity > 1) LocalContentColor.current else Color.Gray,
                        modifier = Modifier
                            .size(32.dp)
                            .clickable(quantity > 1) {
                                onQuantityChange(quantity - 1)
                            }
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
            TextButton(onClick = {
                isEditingTitle = false
                focusManager.clearFocus()
                onDismiss()
            }) {
                Text(stringResource(R.string.cancel))
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = {
                    isEditingTitle = false
                    focusManager.clearFocus()
                    onConfirmItem(title, quantity, category)
                }
            ) {
                Text(stringResource(R.string.ok))
            }
        }

        BackHandler(shouldOpenBottomSheet) {
            onDismiss()
        }
    }
}

@Preview(backgroundColor = 0xFFFFFFFF)
@Composable
fun CreateMarketItemBottomSheetPreview() {
    MaliTheme {
        CreateMarketItemBottomSheet(
            "", {}, 1, {}, null, {}, false, false, { t, q, c -> }, {},
        )
    }
}