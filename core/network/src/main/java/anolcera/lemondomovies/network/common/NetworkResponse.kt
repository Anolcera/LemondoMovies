package anolcera.lemondomovies.network.common

import java.io.IOException

/**
 * Wrapper class for network responses
 */
sealed class NetworkResponse<out T> {
    /**
     * Success response with body
     */
    data class Success<T : Any>(val body: T) : NetworkResponse<T>()

    /**
     * Success response without body
     */
    data object SuccessNoBody : NetworkResponse<Nothing>()

    /**
     * Failure response with body
     */
    data class ApiError(val body: ErrorBody, val code: Int) : NetworkResponse<Nothing>()

    /**
     * Network error
     */
    data class NetworkError(val error: IOException) : NetworkResponse<Nothing>()

    /**
     * For example, json parsing error
     */
    data class UnknownError(val error: Throwable) : NetworkResponse<Nothing>()
}
