package anolcera.lemondomovies.network.common

import kotlinx.serialization.Serializable

@Serializable
data class ErrorBody(
    val code: String,
    val errors: List<String>? = null
)
