package com.joaoferreira.domain.usecases

import com.joaoferreira.domain.models.MarketItem
import com.joaoferreira.domain.repositories.MarketListRepository
import kotlinx.coroutines.flow.Flow

class GetByIdMarketItemUseCase(
    private val repository: MarketListRepository,
): UseCase<GetByIdMarketItemUseCase.Params, MarketItem>() {
    override suspend fun invoke(params: Params): Flow<MarketItem> = repository.getById(params.id)

    data class Params(
        val id: String,
    ): UseCase.Params()
}