package com.joaoferreira.domain.datasources

import com.joaoferreira.domain.models.MarketItem
import kotlinx.coroutines.flow.Flow

interface MarketListRemoteDatasource {
    suspend fun create(marketItem: MarketItem)
    suspend fun getAll(): Flow<List<MarketItem>>
    suspend fun getById(id: String): Flow<MarketItem>
    suspend fun update(marketItem: MarketItem)
    suspend fun deleteById(id: String)
    suspend fun deleteAllDone()
}