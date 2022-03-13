package com.joaoferreira.mali.supermarket_list.viewmodels

import androidx.lifecycle.ViewModel
import com.joaoferreira.domain.usecases.*

class SupermarketListViewModel(
    private val createMarketItemUseCase: CreateMarketItemUseCase,
    private val getAllMarketItemsUseCase: GetAllMarketItemsUseCase,
    private val getByIdMarketItemUseCase: GetByIdMarketItemUseCase,
    private val updateMarketItemUseCase: UpdateMarketItemUseCase,
    private val deleteAllDoneMarketItemUseCase: DeleteAllDoneMarketItemUseCase,
    private val deleteByIdMarketItemUseCase: DeleteByIdMarketItemUseCase,
) : ViewModel()