package com.joaoferreira.mali.supermarket_list.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ErrorContent(hasError: Boolean, modifier: Modifier = Modifier, errorContent: @Composable BoxScope.() -> Unit, content: @Composable ColumnScope.() -> Unit) {
    Box(modifier = modifier) {
        if(hasError) {
            errorContent()
        } else {
            Column {
                content()
            }
        }
    }
}