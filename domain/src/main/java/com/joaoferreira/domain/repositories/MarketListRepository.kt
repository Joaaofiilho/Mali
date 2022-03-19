package com.joaoferreira.domain.repositories

import com.joaoferreira.domain.models.MarketItem
import kotlinx.coroutines.flow.Flow

interface MarketListRepository {
    suspend fun create(marketItem: MarketItem): Result<MarketItem>
    fun getAll(): Flow<Result<List<MarketItem>>>
    fun getById(id: String): Flow<Result<MarketItem>>
    suspend fun update(marketItem: MarketItem): Result<MarketItem>
    suspend fun deleteById(id: String): Result<MarketItem>
    suspend fun deleteAllDone(): Result<List<MarketItem>>
}