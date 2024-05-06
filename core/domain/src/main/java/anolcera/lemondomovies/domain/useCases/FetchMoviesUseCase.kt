package anolcera.lemondomovies.domain.useCases

import anolcera.lemondomovies.domain.models.DiscoverMovieModel
import anolcera.lemondomovies.domain.repositories.RemoteMoviesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchMoviesUseCase @Inject constructor(
    private val remoteMoviesRepository: RemoteMoviesRepository
) {
    suspend operator fun invoke(): Flow<DiscoverMovieModel> =
        remoteMoviesRepository.fetchMovies()
}