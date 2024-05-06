package anolcera.lemondomovies.data.remote

import anolcera.lemondomovies.data.remote.models.DiscoverMovieResponse
import anolcera.lemondomovies.network.common.NetworkResponse
import retrofit2.http.GET

interface TheMovieDBDataSource {

    @GET("discover/movie")
    suspend fun fetchMovies(): NetworkResponse<DiscoverMovieResponse>
}