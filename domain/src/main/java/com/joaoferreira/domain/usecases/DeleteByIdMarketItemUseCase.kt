package com.joaoferreira.domain.usecases

import com.joaoferreira.domain.models.MarketItem
import com.joaoferreira.domain.repositories.MarketListRepository
import kotlinx.coroutines.flow.Flow

class DeleteByIdMarketItemUseCase(
    private val repository: MarketListRepository,
): UseCase<DeleteByIdMarketItemUseCase.Params, MarketItem>() {

    override suspend fun invoke(params: Params): Flow<MarketItem> = repository.deleteById(params.id)

    data class Params(
        val id: String,
    ): UseCase.Params()

}