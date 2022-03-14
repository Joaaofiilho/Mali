package com.joaoferreira.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "market_items")
data class MarketItemModel(
    @SerializedName("_id")
    @PrimaryKey
    val id: String,
    var title: String,
    var quantity: Int,
    @SerializedName("done")
    @ColumnInfo(name = "is_done")
    var isDone: Boolean,
    var category: String?,
)