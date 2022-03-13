package com.joaoferreira.data.datasources

import com.joaoferreira.data.mappers.toMarketItem
import com.joaoferreira.data.mappers.toMarketItemModel
import com.joaoferreira.data.services.MaliApi
import com.joaoferreira.domain.datasources.MarketListRemoteDatasource
import com.joaoferreira.domain.models.MarketItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MarketListRemoteDatasourceImpl(
    private val maliApi: MaliApi
) : MarketListRemoteDatasource {
    override suspend fun create(marketItem: MarketItem) = maliApi.create(marketItem.toMarketItemModel())

    override suspend fun getAll(): Flow<List<MarketItem>> = flow {
        val marketItemModels = maliApi.getAll()
        emit(marketItemModels.map { it.toMarketItem() })
    }

    override suspend fun getById(id: String): Flow<MarketItem> = flow {
        val marketItemModel = maliApi.getById(id)
        emit(marketItemModel.toMarketItem())
    }

    override suspend fun update(marketItem: MarketItem) = maliApi.update(marketItem.id, marketItem.toMarketItemModel())

    override suspend fun deleteById(id: String) = maliApi.deleteById(id)

    override suspend fun deleteAllDone() = maliApi.deleteAllDone()
}