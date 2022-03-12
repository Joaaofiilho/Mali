package com.joaoferreira.data.repositories

import com.joaoferreira.domain.datasources.MarketListLocalDatasource
import com.joaoferreira.domain.datasources.MarketListRemoteDatasource
import com.joaoferreira.domain.models.MarketItem
import com.joaoferreira.domain.repositories.MarketListRepository
import kotlinx.coroutines.flow.Flow

class MarketListRepositoryImpl(
    private val localDatasource: MarketListLocalDatasource,
    private val remoteDatasource: MarketListRemoteDatasource,
): MarketListRepository {
    override suspend fun create(marketItem: MarketItem): Flow<MarketItem> {
        return remoteDatasource.create(marketItem)
    }

    override suspend fun getAll(): Flow<List<MarketItem>> {
        return remoteDatasource.getAll()
    }

    override suspend fun getById(id: String): Flow<MarketItem> {
        return remoteDatasource.getById(id)
    }

    override suspend fun update(marketItem: MarketItem): Flow<MarketItem> {
        return remoteDatasource.update(marketItem)
    }

    override suspend fun deleteById(id: String): Flow<Unit> {
        return remoteDatasource.deleteById(id)
    }

    override suspend fun deleteAllDone(): Flow<Unit> {
        return remoteDatasource.deleteAllDone()
    }
}