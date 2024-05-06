package anolcera.lemondomovies.domain.repositories

import anolcera.lemondomovies.domain.models.DiscoverMovieModel
import kotlinx.coroutines.flow.Flow


interface RemoteMoviesRepository {

    suspend fun fetchMovies(): Flow<DiscoverMovieModel>
}