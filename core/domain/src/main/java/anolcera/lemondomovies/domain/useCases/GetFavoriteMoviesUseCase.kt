package anolcera.lemondomovies.domain.useCases

import anolcera.lemondomovies.domain.models.MovieDetailsModel
import anolcera.lemondomovies.domain.repositories.MoviesRepository
import javax.inject.Inject

class GetFavoriteMoviesUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) {
    suspend operator fun invoke(): List<MovieDetailsModel>? {
        return moviesRepository.getFavoriteMovies()
    }
}