package com.joaoferreira.data.mappers

import com.joaoferreira.data.models.MarketItemModel
import com.joaoferreira.domain.models.MarketItem

fun MarketItem.toMarketItemModel() = MarketItemModel(
    this.id,
    this.title,
    this.quantity,
    this.isDone,
    this.category
)

fun MarketItemModel.toMarketItem() = MarketItem(
    this.id,
    this.title,
    this.quantity,
    this.isDone,
    this.category
)