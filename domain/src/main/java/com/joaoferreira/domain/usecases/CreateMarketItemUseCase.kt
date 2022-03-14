package com.joaoferreira.domain.usecases

import com.joaoferreira.domain.models.MarketItem
import com.joaoferreira.domain.repositories.MarketListRepository
import kotlinx.coroutines.flow.Flow

class CreateMarketItemUseCase(
    private val repository: MarketListRepository,
) : SuspendUseCase<CreateMarketItemUseCase.Params, MarketItem>() {
    override suspend operator fun invoke(params: Params): MarketItem = repository.create(params.marketItem)

    data class Params(
        val marketItem: MarketItem
    ) : UseCase.Params()
}