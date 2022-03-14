package com.joaoferreira.domain.usecases

import com.joaoferreira.domain.models.MarketItem
import com.joaoferreira.domain.repositories.MarketListRepository
import kotlinx.coroutines.flow.Flow

class UpdateMarketItemUseCase(
    private val repository: MarketListRepository,
): UseCase<UpdateMarketItemUseCase.Params, Flow<MarketItem>>() {

    override fun invoke(params: Params): Flow<MarketItem> = repository.update(params.marketItem)

    data class Params(
        val marketItem: MarketItem,
    ): UseCase.Params()

}