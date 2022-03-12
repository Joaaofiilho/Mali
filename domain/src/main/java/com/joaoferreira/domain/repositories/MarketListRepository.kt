package com.joaoferreira.domain.repositories

import com.joaoferreira.domain.models.MarketItem
import kotlinx.coroutines.flow.Flow

interface MarketListRepository {
    suspend fun create(marketItem: MarketItem): Flow<MarketItem>
    suspend fun getAll(): Flow<List<MarketItem>>
    suspend fun getById(id: String): Flow<MarketItem>
    suspend fun update(marketItem: MarketItem): Flow<MarketItem>
    suspend fun deleteById(id: String): Flow<Unit>
    suspend fun deleteAllDone(): Flow<Unit>
}