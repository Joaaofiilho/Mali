package com.joaoferreira.data.repositories

import com.joaoferreira.domain.datasources.MarketListLocalDatasource
import com.joaoferreira.domain.datasources.MarketListRemoteDatasource
import com.joaoferreira.domain.models.MarketItem
import com.joaoferreira.domain.repositories.MarketListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single

class MarketListRepositoryImpl(
    private val localDatasource: MarketListLocalDatasource,
    private val remoteDatasource: MarketListRemoteDatasource,
): MarketListRepository {
    override suspend fun create(marketItem: MarketItem): Flow<MarketItem> = flow {
        localDatasource.create(marketItem)
        try {
            remoteDatasource.create(marketItem)
            emit(marketItem)
        } catch (e: Throwable) {
            localDatasource.deleteById(marketItem.id)
            throw e
        }
    }

    override suspend fun getAll(): Flow<List<MarketItem>> = flow {
        localDatasource.getAll()
            .collect {
                emit(it)
            }
        val marketItems = remoteDatasource.getAll().single()
        localDatasource.createAll(marketItems)
    }

    override suspend fun getById(id: String): Flow<MarketItem> = flow {
        localDatasource.getById(id).collect {
            emit(it)
        }

        val marketItem = remoteDatasource.getById(id).single()
        localDatasource.create(marketItem)
    }

    override suspend fun update(marketItem: MarketItem): Flow<MarketItem> = flow {
        val oldMarketItem = localDatasource.getById(marketItem.id).single()

        localDatasource.update(marketItem)
        try {
            remoteDatasource.update(marketItem)
            emit(marketItem)
        } catch (e: Throwable) {
            localDatasource.update(oldMarketItem)
            throw e
        }
    }

    override suspend fun deleteById(id: String): Flow<MarketItem> = flow {
        val marketItem = localDatasource.getById(id).single()

        localDatasource.deleteById(id)
        try {
            remoteDatasource.deleteById(id)
            emit(marketItem)
        } catch(e: Throwable) {
            localDatasource.create(marketItem)
            throw e
        }
    }

    override suspend fun deleteAllDone(): Flow<List<MarketItem>> = flow {
        val doneMarketItems = localDatasource.getAllDone().single()

        localDatasource.deleteAllDone()
        try {
            remoteDatasource.deleteAllDone()
            emit(doneMarketItems)
        } catch (e: Throwable) {
            localDatasource.createAll(doneMarketItems)
            throw e
        }
    }
}