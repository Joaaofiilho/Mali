package com.joaoferreira.data.datasources

import com.joaoferreira.data.database.MaliDatabase
import com.joaoferreira.data.mappers.toMarketItem
import com.joaoferreira.data.mappers.toMarketItemModel
import com.joaoferreira.domain.datasources.MarketListLocalDatasource
import com.joaoferreira.domain.models.MarketItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class MarketListLocalDatasourceImpl(
    private val database: MaliDatabase,
) : MarketListLocalDatasource {
    override suspend fun create(marketItem: MarketItem) =
        database.marketItemsDao().insert(marketItem.toMarketItemModel())

    override suspend fun createAll(marketItems: List<MarketItem>) =
        database.marketItemsDao().insertAll(marketItems.map { it.toMarketItemModel() })

    override fun getAll(): Flow<List<MarketItem>> = database.marketItemsDao().getAll()
        .map { marketItemModels -> marketItemModels.map { it.toMarketItem() } }

    override fun getAllDone(): Flow<List<MarketItem>> =
        database.marketItemsDao().getAllDone()
            .map { marketItemModels -> marketItemModels.map { it.toMarketItem() } }

    override fun getById(id: String): Flow<MarketItem> =
        database.marketItemsDao().getById(id).map { it.toMarketItem() }

    override suspend fun update(marketItem: MarketItem) =
        database.marketItemsDao().update(marketItem.toMarketItemModel())

    override suspend fun deleteById(id: String) = database.marketItemsDao().deleteById(id)

    override suspend fun deleteAllDone() = database.marketItemsDao().deleteAllDone()
}