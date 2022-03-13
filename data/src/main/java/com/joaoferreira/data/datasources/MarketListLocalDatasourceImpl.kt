package com.joaoferreira.data.datasources

import com.joaoferreira.data.database.MaliDatabase
import com.joaoferreira.domain.datasources.MarketListLocalDatasource
import com.joaoferreira.domain.models.MarketItem
import kotlinx.coroutines.flow.Flow

class MarketListLocalDatasourceImpl(
    private val database: MaliDatabase,
): MarketListLocalDatasource {
    override suspend fun create(marketItem: MarketItem) = database.marketItemsDao().insert(marketItem)

    override suspend fun createAll(marketItems: List<MarketItem>) {
        TODO("Not yet implemented")
    }

    override suspend fun getAll(): Flow<List<MarketItem>> = database.marketItemsDao().getAll()

    override suspend fun getAllDone(): Flow<List<MarketItem>> = database.marketItemsDao().getAllDone()

    override suspend fun getById(id: String): Flow<MarketItem> = database.marketItemsDao().getById(id)

    override suspend fun update(marketItem: MarketItem) = database.marketItemsDao().update(marketItem)

    override suspend fun deleteById(id: String) = database.marketItemsDao().deleteById(id)

    override suspend fun deleteAllDone() = database.marketItemsDao().deleteAllDone()
}