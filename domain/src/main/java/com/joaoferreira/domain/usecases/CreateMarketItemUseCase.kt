package com.joaoferreira.domain.usecases

import com.joaoferreira.domain.models.MarketItem
import com.joaoferreira.domain.repositories.MarketListRepository
import com.joaoferreira.domain.utils.Categories
import com.joaoferreira.domain.utils.FieldFailure
import com.joaoferreira.domain.utils.Fields

class CreateMarketItemUseCase(
    private val repository: MarketListRepository,
) : SuspendUseCase<CreateMarketItemUseCase.Params, Result<MarketItem>>() {
    override suspend operator fun invoke(params: Params): Result<MarketItem> {
        if (params.title.isEmpty()) {
            return Result.failure(FieldFailure(Fields.TITLE))
        }

        if (params.quantity < 0) {
            return Result.failure(FieldFailure(Fields.QUANTITY))
        }

        return repository.create(
            MarketItem(
                "",
                params.title,
                params.quantity,
                params.isDone,
                params.category
            )
        )
    }

    data class Params(
        val title: String,
        val quantity: Int,
        val category: Categories?,
        val isDone: Boolean = false,
    ) : UseCase.Params()
}