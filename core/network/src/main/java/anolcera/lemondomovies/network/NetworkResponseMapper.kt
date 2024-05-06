package anolcera.lemondomovies.network

import anolcera.lemondomovies.network.common.NetworkErrorType
import anolcera.lemondomovies.network.common.NetworkResponse
import anolcera.lemondomovies.network.common.exception.NetworkException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

fun <T> NetworkResponse<T>.mapResult(): Flow<T> = flow {
    when (val networkResponse = this@mapResult) {
        is NetworkResponse.Success -> emit(networkResponse.body)
        is NetworkResponse.ApiError -> networkResponse.apiError()
        is NetworkResponse.NetworkError -> throw NetworkException(
            NetworkErrorType.NoInternet,
            networkResponse.error.message ?: ""
        )

        is NetworkResponse.UnknownError -> throw NetworkException(
            NetworkErrorType.BadRequest,
            networkResponse.error.message ?: ""
        )

        is NetworkResponse.SuccessNoBody -> throw NetworkException(
            NetworkErrorType.EmptyResponse,
            ""
        )
    }
}

private fun <T> NetworkResponse.ApiError.apiError(): T {
    throw when (code) {
        500 -> NetworkException(NetworkErrorType.ServerError, body.code, errorBody = body)
        401 -> NetworkException(NetworkErrorType.UnauthorizedError, body.code, errorBody = body)
        403 -> NetworkException(NetworkErrorType.Forbidden, body.code, errorBody = body)
        404 -> NetworkException(NetworkErrorType.BadRequest, body.code, errorBody = body)
        else -> NetworkException(NetworkErrorType.BadRequest, body.code, errorBody = body)
    }
}
