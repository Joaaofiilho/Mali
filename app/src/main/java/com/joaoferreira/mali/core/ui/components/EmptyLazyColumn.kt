package com.joaoferreira.mali.core.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun EmptyLazyColumn(isEmpty: Boolean, modifier: Modifier = Modifier, emptyContent: @Composable BoxScope.() -> Unit, content: LazyListScope.() -> Unit) {
    Box(modifier = modifier) {
        if(isEmpty) {
            emptyContent()
        } else {
            LazyColumn {
                content()
            }
        }
    }
}