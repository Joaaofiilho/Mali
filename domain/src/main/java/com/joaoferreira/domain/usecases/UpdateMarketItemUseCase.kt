package com.joaoferreira.domain.usecases

import com.joaoferreira.domain.models.MarketItem
import com.joaoferreira.domain.repositories.MarketListRepository
import kotlinx.coroutines.flow.Flow

class UpdateMarketItemUseCase(
    private val repository: MarketListRepository,
): SuspendUseCase<UpdateMarketItemUseCase.Params, Result<MarketItem>>() {

    override suspend fun invoke(params: Params): Result<MarketItem> = repository.update(params.marketItem)

    data class Params(
        val marketItem: MarketItem,
    ): UseCase.Params()

}