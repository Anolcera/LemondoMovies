package anolcera.lemondomovies.network.common

enum class NetworkErrorType {
    BadRequest,
    ServerError,
    UnauthorizedError,
    EmptyResponse,
    NoInternet,
    Forbidden;
}
