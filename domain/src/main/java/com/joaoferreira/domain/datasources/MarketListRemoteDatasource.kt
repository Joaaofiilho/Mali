package com.joaoferreira.domain.datasources

import com.joaoferreira.domain.models.MarketItem
import kotlinx.coroutines.flow.Flow

interface MarketListRemoteDatasource {
    suspend fun create(marketItem: MarketItem): MarketItem
    suspend fun getAll(): List<MarketItem>
    suspend fun getById(id: String): MarketItem
    suspend fun update(marketItem: MarketItem)
    suspend fun deleteById(id: String)
    suspend fun deleteAllDone()
}