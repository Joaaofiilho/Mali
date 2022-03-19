package com.joaoferreira.domain.models

import com.joaoferreira.domain.utils.Categories

data class MarketItem(
    val id: String,
    var title: String,
    var quantity: Int,
    var isDone: Boolean,
    var category: Categories?,
)