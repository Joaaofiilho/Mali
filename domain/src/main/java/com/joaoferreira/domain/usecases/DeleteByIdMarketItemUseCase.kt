package com.joaoferreira.domain.usecases

import com.joaoferreira.domain.models.MarketItem
import com.joaoferreira.domain.repositories.MarketListRepository
import kotlinx.coroutines.flow.Flow

class DeleteByIdMarketItemUseCase(
    private val repository: MarketListRepository,
): SuspendUseCase<DeleteByIdMarketItemUseCase.Params, Result<MarketItem>>() {

    override suspend fun invoke(params: Params): Result<MarketItem> = repository.deleteById(params.id)

    data class Params(
        val id: String,
    ): UseCase.Params()

}