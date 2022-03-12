package com.joaoferreira.data.models

import com.google.gson.annotations.SerializedName

data class MarketItemModel(
    val id: String,
    var title: String,
    var quantity: Int,
    @SerializedName("done")
    var isDone: Boolean,
    var category: String?,
)