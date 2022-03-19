package com.joaoferreira.mali.supermarket_list.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.joaoferreira.mali.core.ui.theme.MaliTheme

@Composable
fun QuantityContent(quantity: Int, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        QuantityBadge(quantity > 2)
        Spacer(modifier = Modifier.height(1.dp))
        QuantityBadge(quantity > 1)
        Spacer(modifier = Modifier.height(1.dp))
        QuantityBadge(quantity > 0)
        Spacer(modifier = Modifier.height(1.dp))
        Text(text = quantity.toString(), style = MaterialTheme.typography.h6, modifier = Modifier.align(Alignment.CenterHorizontally))
    }
}

@Composable
private fun QuantityBadge(filled: Boolean) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = if (filled) MaterialTheme.colors.primary else Color.Transparent,
        border = BorderStroke(1.dp, color = MaterialTheme.colors.primary),
        modifier = Modifier
            .fillMaxWidth()
            .height(4.dp),
    ) {

    }
}

@Preview(widthDp = 20)
@Composable
private fun QuantityContentPreview() {
    MaliTheme {
        QuantityContent(3)
    }
}