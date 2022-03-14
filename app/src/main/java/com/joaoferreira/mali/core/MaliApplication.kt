package com.joaoferreira.mali.core

import android.app.Application
import com.joaoferreira.data.database.MaliDatabase
import com.joaoferreira.data.datasources.MarketListLocalDatasourceImpl
import com.joaoferreira.data.datasources.MarketListRemoteDatasourceImpl
import com.joaoferreira.data.repositories.MarketListRepositoryImpl
import com.joaoferreira.data.services.getService
import com.joaoferreira.domain.datasources.MarketListLocalDatasource
import com.joaoferreira.domain.datasources.MarketListRemoteDatasource
import com.joaoferreira.domain.repositories.MarketListRepository
import com.joaoferreira.domain.usecases.*
import com.joaoferreira.mali.BuildConfig
import com.joaoferreira.mali.supermarket_list.viewmodels.SupermarketListViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.dsl.module

class MaliApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        val localDatasourcesModule = module {
            single { MaliDatabase.getDatabase(androidContext()) }
            single<MarketListLocalDatasource> { MarketListLocalDatasourceImpl(database = get()) }
        }

        val remoteDatasourcesModule = module {
            single { getService() }
            single<MarketListRemoteDatasource> { MarketListRemoteDatasourceImpl(maliApi = get()) }
        }

        val repositoriesModule = module {
            single<MarketListRepository> {
                MarketListRepositoryImpl(
                    localDatasource = get(),
                    remoteDatasource = get()
                )
            }
        }

        val usecasesModule = module {
            single { CreateMarketItemUseCase(repository = get()) }
            single { GetAllMarketItemsUseCase(repository = get()) }
            single { GetByIdMarketItemUseCase(repository = get()) }
            single { UpdateMarketItemUseCase(repository = get()) }
            single { DeleteAllDoneMarketItemUseCase(repository = get()) }
            single { DeleteByIdMarketItemUseCase(repository = get()) }
        }

        val viewModelsModule = module {
            viewModel {
                SupermarketListViewModel(
                    get(), get(), get(), get(), get(), get(),
                )
            }
        }

        startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@MaliApplication)
            modules(
                localDatasourcesModule,
                remoteDatasourcesModule,
                repositoriesModule,
                usecasesModule,
                viewModelsModule
            )
        }
    }
}