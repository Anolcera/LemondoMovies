package anolcera.lemondomovies.domain.useCases

import anolcera.lemondomovies.domain.repositories.MoviesRepository
import javax.inject.Inject

class RemoveMovieFromFavoritesUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) {
    suspend operator fun invoke(remoteId: Int) {
        moviesRepository.removeMovieFromFavorites(remoteId)
    }
}