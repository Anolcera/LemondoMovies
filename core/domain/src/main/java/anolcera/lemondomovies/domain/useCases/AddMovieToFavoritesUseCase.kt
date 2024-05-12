package anolcera.lemondomovies.domain.useCases

import anolcera.lemondomovies.domain.models.MovieDetailsModel
import anolcera.lemondomovies.domain.repositories.MoviesRepository
import javax.inject.Inject

class AddMovieToFavoritesUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) {
    suspend operator fun invoke(movie: MovieDetailsModel) {
        moviesRepository.addMovieToFavorites(movie)
    }
}