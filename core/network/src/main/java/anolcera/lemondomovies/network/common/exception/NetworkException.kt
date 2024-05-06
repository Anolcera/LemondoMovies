package anolcera.lemondomovies.network.common.exception

import anolcera.lemondomovies.network.common.ErrorBody
import anolcera.lemondomovies.network.common.NetworkErrorType

@Suppress("unused")
class NetworkException(
    val error: NetworkErrorType,
    val text: String,
    val errorBody: ErrorBody? = null
) : Throwable(text)
