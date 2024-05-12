package anolcera.lemondomovies.domain.useCases

import anolcera.lemondomovies.domain.models.MovieDetailsModel
import anolcera.lemondomovies.domain.repositories.MoviesRepository
import javax.inject.Inject

class GetFavoriteMovieByIdUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) {

    suspend operator fun invoke(remoteId: Int): MovieDetailsModel? =
        moviesRepository.getFavoriteMovieById(remoteId)
}