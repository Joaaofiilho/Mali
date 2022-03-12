package com.joaoferreira.domain.datasources

import com.joaoferreira.domain.models.MarketItem
import kotlinx.coroutines.flow.Flow

interface MarketListLocalDatasource {
    suspend fun create(marketItem: MarketItem): Flow<MarketItem>
    suspend fun getAll(): Flow<List<MarketItem>>
    suspend fun getById(id: String): Flow<MarketItem>
    suspend fun update(marketItem: MarketItem): Flow<MarketItem>
    suspend fun deleteById(id: String): Flow<MarketItem>
    suspend fun deleteAllDone(): Flow<List<MarketItem>>
}