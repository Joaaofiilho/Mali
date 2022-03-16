package com.joaoferreira.data.repositories

import com.joaoferreira.domain.datasources.MarketListLocalDatasource
import com.joaoferreira.domain.datasources.MarketListRemoteDatasource
import com.joaoferreira.domain.models.MarketItem
import com.joaoferreira.domain.repositories.MarketListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MarketListRepositoryImpl(
    private val localDatasource: MarketListLocalDatasource,
    private val remoteDatasource: MarketListRemoteDatasource,
): MarketListRepository {
    override suspend fun create(marketItem: MarketItem): MarketItem {
        val createdMarketItem = remoteDatasource.create(marketItem)
        localDatasource.create(createdMarketItem)
        return createdMarketItem
    }

    override fun getAll(): Flow<List<MarketItem>> = flow {
        coroutineScope {
            launch {
                val marketItems = remoteDatasource.getAll()
                localDatasource.createAll(marketItems)
            }

            localDatasource.getAll()
                .collect {
                    emit(it)
                }
        }
    }.flowOn(Dispatchers.IO)

    override fun getById(id: String): Flow<MarketItem> = flow {
        coroutineScope {
            launch {
                val marketItem = remoteDatasource.getById(id)
                localDatasource.create(marketItem)
            }

            localDatasource.getById(id).collect {
                emit(it)
            }
        }
    }.flowOn(Dispatchers.IO)

    override fun update(marketItem: MarketItem): Flow<MarketItem> = flow {
        val oldMarketItem = localDatasource.getById(marketItem.id).first()
        localDatasource.update(marketItem)

        try {
            remoteDatasource.update(marketItem)
            emit(marketItem)
        } catch (e: Throwable) {
            localDatasource.update(oldMarketItem)
            throw e
        }
    }.flowOn(Dispatchers.IO)

    override fun deleteById(id: String): Flow<MarketItem> = flow {
        val marketItem = localDatasource.getById(id).first()

        localDatasource.deleteById(id)
        try {
            remoteDatasource.deleteById(id)
            emit(marketItem)
        } catch(e: Throwable) {
            localDatasource.create(marketItem)
            throw e
        }
    }.flowOn(Dispatchers.IO)

    override fun deleteAllDone(): Flow<List<MarketItem>> = flow {
        val doneMarketItems = localDatasource.getAllDone().first()

        localDatasource.deleteAllDone()
        try {
            remoteDatasource.deleteAllDone()
            emit(doneMarketItems)
        } catch (e: Throwable) {
            localDatasource.createAll(doneMarketItems)
            throw e
        }
    }.flowOn(Dispatchers.IO)
}