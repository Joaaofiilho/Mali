package com.joaoferreira.domain.datasources

import com.joaoferreira.domain.models.MarketItem
import kotlinx.coroutines.flow.Flow

interface MarketListLocalDatasource {
    suspend fun create(marketItem: MarketItem)
    suspend fun createAll(marketItems: List<MarketItem>)
    fun getAll(): Flow<List<MarketItem>>
    fun getAllDone(): Flow<List<MarketItem>>
    fun getById(id: String): Flow<MarketItem>
    suspend fun update(marketItem: MarketItem)
    suspend fun deleteById(id: String)
    suspend fun deleteAllDone()
}