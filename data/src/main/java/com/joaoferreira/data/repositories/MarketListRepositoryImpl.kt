package com.joaoferreira.data.repositories

import com.joaoferreira.data.utils.requestThrowableToFailure
import com.joaoferreira.domain.datasources.MarketListLocalDatasource
import com.joaoferreira.domain.datasources.MarketListRemoteDatasource
import com.joaoferreira.domain.models.MarketItem
import com.joaoferreira.domain.repositories.MarketListRepository
import com.joaoferreira.domain.utils.LocalDatasourceFailure
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

class MarketListRepositoryImpl(
    private val localDatasource: MarketListLocalDatasource,
    private val remoteDatasource: MarketListRemoteDatasource,
) : MarketListRepository {
    override suspend fun create(marketItem: MarketItem): Result<MarketItem> {
        return try {
            val createdMarketItem = remoteDatasource.create(marketItem)
            try {
                localDatasource.create(createdMarketItem)
                Result.success(marketItem)
            } catch (e: Throwable) {
                Result.failure(LocalDatasourceFailure())
            }
        } catch (e: Throwable) {
            Result.failure(requestThrowableToFailure(e))
        }
    }

    override fun getAll(): Flow<Result<List<MarketItem>>> = flow<Result<List<MarketItem>>> {
        try {
            val marketItems = remoteDatasource.getAll()
            try {
                localDatasource.createAll(marketItems)
            } catch (e: Throwable) {
                emit(Result.failure(LocalDatasourceFailure()))
            }

            localDatasource.getAll()
                .catch {
                    emit(Result.failure(LocalDatasourceFailure()))
                }
                .collect {
                    emit(Result.success(it))
                }
        } catch (e: Throwable) {
            emit(Result.failure(requestThrowableToFailure(e)))
        }
    }.flowOn(Dispatchers.IO)

    override fun getById(id: String): Flow<Result<MarketItem>> = flow<Result<MarketItem>> {
            try {
                val marketItem = remoteDatasource.getById(id)
                try {
                    localDatasource.create(marketItem)
                } catch (e: Throwable) {
                    emit(Result.failure(LocalDatasourceFailure()))
                }
            } catch (e: Throwable) {
                emit(Result.failure(requestThrowableToFailure(e)))
            }

            localDatasource.getById(id)
                .catch {
                    emit(Result.failure(LocalDatasourceFailure()))
                }
                .collect {
                    emit(Result.success(it))
                }
    }.flowOn(Dispatchers.IO)

    override suspend fun update(marketItem: MarketItem): Result<MarketItem> {
        return try {
            remoteDatasource.update(marketItem)
            try {
                localDatasource.update(marketItem)
                Result.success(marketItem)
            } catch (e: Throwable) {
                Result.failure(LocalDatasourceFailure())
            }
        } catch (e: Throwable) {
            Result.failure(requestThrowableToFailure(e))
        }
    }

    override suspend fun deleteById(id: String): Result<MarketItem> {
        return try {
            try {
                remoteDatasource.deleteById(id)
                try {
                    val marketItem = localDatasource.getById(id).first()
                    localDatasource.deleteById(id)
                    Result.success(marketItem)
                } catch (e: Throwable) {
                    Result.failure(LocalDatasourceFailure())
                }
            } catch (e: Throwable) {
                Result.failure(requestThrowableToFailure(e))
            }
        } catch (e: Throwable) {
            Result.failure(LocalDatasourceFailure())
        }
    }

    override suspend fun deleteAllDone(): Result<List<MarketItem>> {
        return try {
            remoteDatasource.deleteAllDone()

            try {
                val doneMarketItems = localDatasource.getAllDone().first()
                localDatasource.deleteAllDone()
                Result.success(doneMarketItems)
            } catch (e: Throwable) {
                Result.failure(LocalDatasourceFailure())
            }
        } catch (e: Throwable) {
            Result.failure(requestThrowableToFailure(e))
        }
    }
}