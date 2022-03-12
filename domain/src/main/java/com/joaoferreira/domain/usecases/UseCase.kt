package com.joaoferreira.domain.usecases

import kotlinx.coroutines.flow.Flow

abstract class UseCase<P: UseCase.Params, R> {
    abstract class Params
    abstract suspend operator fun invoke(params: P): Flow<R>
}

class NoParams: UseCase.Params()