package com.joaoferreira.mali.supermarket_list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.joaoferreira.domain.models.MarketItem

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SupermarketItem(
    marketItem: MarketItem,
    loading: Boolean,
    dismissState: DismissState,
    modifier: Modifier = Modifier,
    onItemClicked: () -> Unit
) {
    SwipeToDismiss(
        state = dismissState,
        directions = setOf(DismissDirection.EndToStart),
        dismissThresholds = { FixedThreshold(100.dp) },
        background = {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colors.error),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(modifier = Modifier.size(24.dp)) {
                    if (loading) {
                        CircularProgressIndicator(
                            strokeWidth = 2.dp,
                            modifier = Modifier.fillMaxWidth(),
                            color = MaterialTheme.colors.onError
                        )
                    } else {
                        Icon(
                            Icons.Default.Delete,
                            "",
                            tint = MaterialTheme.colors.onError,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.width(16.dp))
            }
        }) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .clickable(enabled = !loading) {
                    onItemClicked()
                }
                .background(color = MaterialTheme.colors.surface)
                .then(modifier)

        ) {
            QuantityContent(marketItem.quantity, modifier = Modifier.width(20.dp).offset(y = 4.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                marketItem.title,
                color = Color.Black,
                modifier = Modifier.weight(1f),
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.width(8.dp))

            Box(modifier = Modifier.size(24.dp)) {
                if (loading) {
                    CircularProgressIndicator(
                        strokeWidth = 2.dp,
                        modifier = Modifier.fillMaxWidth()
                    )
                } else {
                    Checkbox(
                        checked = marketItem.isDone,
                        onCheckedChange = {
                            onItemClicked()
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
fun SupermarketItemComponentPreview() {
    SupermarketItem(
        MarketItem(
            "",
            "Pãozinho ótimo com maionese e alho. Nossa que bom que é!",
            3,
            false,
            null
        ),
        false, dismissState = rememberDismissState(),
    ) {

    }
}