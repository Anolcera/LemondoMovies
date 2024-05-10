package anolcera.lemondomovies.data.remote

import anolcera.lemondomovies.data.remote.models.DiscoverMovieResponse
import anolcera.lemondomovies.network.common.NetworkResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TheMovieDBDataSource {

    @GET("discover/movie")
    suspend fun fetchMovies(
        @Query("page") page: Int,
    ): NetworkResponse<DiscoverMovieResponse>
}