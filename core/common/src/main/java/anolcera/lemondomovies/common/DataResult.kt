package anolcera.lemondomovies.common

sealed interface DataResult<out T> {

    data class Success<T>(val data: T) : DataResult<T>

    data class Error(val exception: Throwable) : DataResult<Nothing>
}