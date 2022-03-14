package com.joaoferreira.domain.usecases

import com.joaoferreira.domain.models.MarketItem
import com.joaoferreira.domain.repositories.MarketListRepository
import kotlinx.coroutines.flow.Flow

class GetAllMarketItemsUseCase(
    private val repository: MarketListRepository,
): UseCase<NoParams, Flow<List<MarketItem>>>() {
    override fun invoke(params: NoParams): Flow<List<MarketItem>> = repository.getAll()
}