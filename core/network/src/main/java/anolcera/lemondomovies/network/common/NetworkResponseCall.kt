package anolcera.lemondomovies.network.common

import anolcera.lemondomovies.network.common.exception.EmptyErrorBodyException
import kotlinx.serialization.json.Json
import okhttp3.Request
import okio.IOException
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NetworkResponseCall<ResultType : Any>(
    private val delegate: Call<ResultType>,
    private val json: Json
) : Call<NetworkResponse<ResultType>> {

    override fun enqueue(callback: Callback<NetworkResponse<ResultType>>) {
        delegate.enqueue(object : Callback<ResultType> {
            override fun onResponse(call: Call<ResultType>, response: Response<ResultType>) {
                val body = response.body()
                val code = response.code()
                val error = response.errorBody()

                if (response.isSuccessful) {
                    if (body != null) {
                        callback.onResponse(
                            this@NetworkResponseCall,
                            Response.success(NetworkResponse.Success(body))
                        )
                    } else {
                        callback.onResponse(
                            this@NetworkResponseCall,
                            Response.success(NetworkResponse.SuccessNoBody)
                        )
                    }
                } else {
                    val errorBody = error?.string() ?: ""
                    val errorText = try {
                        json.decodeFromString(errorBody)
                    } catch (e: Exception) {
                        ErrorBody(code = "")
                    }
                    if (error != null && errorText.code.isNotEmpty()) {
                        callback.onResponse(
                            this@NetworkResponseCall,
                            Response.success(NetworkResponse.ApiError(errorText, code))
                        )
                    } else if ((400..600).contains(code)) {
                        callback.onResponse(
                            this@NetworkResponseCall,
                            Response.success(NetworkResponse.ApiError(errorText, code))
                        )
                    } else {
                        callback.onResponse(
                            this@NetworkResponseCall,
                            Response.success(
                                NetworkResponse.UnknownError(
                                    EmptyErrorBodyException()
                                )
                            )
                        )
                    }
                }
            }

            override fun onFailure(call: Call<ResultType>, throwable: Throwable) {
                val networkResponse = when (throwable) {
                    is IOException -> NetworkResponse.NetworkError(throwable)
                    else -> NetworkResponse.UnknownError(throwable)
                }
                callback.onResponse(this@NetworkResponseCall, Response.success(networkResponse))
            }
        })
    }

    override fun isExecuted() = delegate.isExecuted

    override fun clone() = NetworkResponseCall(delegate.clone(), json)

    override fun isCanceled() = delegate.isCanceled

    override fun cancel() = delegate.cancel()

    override fun execute(): Response<NetworkResponse<ResultType>> {
        throw UnsupportedOperationException("NetworkResponseCall doesn't support execute")
    }

    override fun request(): Request = delegate.request()

    override fun timeout(): Timeout = delegate.timeout()
}
