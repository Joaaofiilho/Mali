package com.joaoferreira.domain.usecases

abstract class UseCase<P: UseCase.Params, R> {
    abstract class Params
    abstract operator fun invoke(params: P): R
}

abstract class SuspendUseCase<P: UseCase.Params, R> {
    abstract suspend operator fun invoke(params: P): R
}

class NoParams: UseCase.Params()