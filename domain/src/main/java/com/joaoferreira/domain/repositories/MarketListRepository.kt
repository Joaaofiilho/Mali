package com.joaoferreira.domain.repositories

import com.joaoferreira.domain.models.MarketItem
import kotlinx.coroutines.flow.Flow

interface MarketListRepository {
    suspend fun create(marketItem: MarketItem): MarketItem
    fun getAll(): Flow<List<MarketItem>>
    fun getById(id: String): Flow<MarketItem>
    fun update(marketItem: MarketItem): Flow<MarketItem>
    fun deleteById(id: String): Flow<MarketItem>
    fun deleteAllDone(): Flow<List<MarketItem>>
}