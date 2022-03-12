package com.joaoferreira.domain.models

data class MarketItem(
    val id: String,
    var title: String,
    var quantity: Int,
    var isDone: Boolean,
    var category: String?,
)