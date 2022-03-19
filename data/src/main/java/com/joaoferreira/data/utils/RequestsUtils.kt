package com.joaoferreira.data.utils

import com.joaoferreira.domain.utils.Failure
import com.joaoferreira.domain.utils.RemoteDatasourceFailure
import com.joaoferreira.domain.utils.RequestErrors
import retrofit2.HttpException
import java.net.SocketException
import java.net.UnknownHostException

fun requestThrowableToFailure(e: Throwable): Failure {
    return when(e) {
        is SocketException -> RemoteDatasourceFailure(RequestErrors.BAD_INTERNET_ERROR)
        is HttpException -> {
            when(e.code()) {
                in 400..499 -> RemoteDatasourceFailure(RequestErrors.CLIENT_SIDE_ERROR)
                in 500..599 -> RemoteDatasourceFailure(RequestErrors.SERVER_SIDE_ERROR)
                else -> RemoteDatasourceFailure(RequestErrors.UNKNOWN_ERROR)
            }
        }
        is UnknownHostException -> RemoteDatasourceFailure(RequestErrors.UNKNOWN_ERROR)
        else -> RemoteDatasourceFailure(RequestErrors.UNKNOWN_ERROR)
    }
}