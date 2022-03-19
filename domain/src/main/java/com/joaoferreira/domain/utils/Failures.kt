package com.joaoferreira.domain.utils

open class Failure : Throwable()
data class RemoteDatasourceFailure(
    val requestError: RequestErrors
) : Failure()

class LocalDatasourceFailure() : Failure()
data class FieldFailure(
    val field: Fields
) : Failure()