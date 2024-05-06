package anolcera.lemondomovies.data.remote

import anolcera.lemondomovies.domain.models.DiscoverMovieModel
import anolcera.lemondomovies.domain.repositories.RemoteMoviesRepository
import anolcera.lemondomovies.network.mapResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class RemoteMoviesRepositoryImpl @Inject constructor(
    private val theMovieDBDataSource: TheMovieDBDataSource
): RemoteMoviesRepository {
    override suspend fun fetchMovies(): Flow<DiscoverMovieModel> {
        return theMovieDBDataSource.fetchMovies().mapResult().map {
            it.toDomainModel()
        }
    }
}