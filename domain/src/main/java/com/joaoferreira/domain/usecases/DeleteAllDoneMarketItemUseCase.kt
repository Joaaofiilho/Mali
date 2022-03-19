package com.joaoferreira.domain.usecases

import com.joaoferreira.domain.models.MarketItem
import com.joaoferreira.domain.repositories.MarketListRepository
import kotlinx.coroutines.flow.Flow

class DeleteAllDoneMarketItemUseCase(
    private val repository: MarketListRepository,
): SuspendUseCase<NoParams, Result<List<MarketItem>>>() {
    override suspend fun invoke(params: NoParams): Result<List<MarketItem>> = repository.deleteAllDone()
}