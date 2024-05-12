package anolcera.lemondomovies.domain.useCases

import anolcera.lemondomovies.domain.models.MovieDetailsModel
import anolcera.lemondomovies.domain.repositories.MoviesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMovieByIdUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) {
    operator fun invoke(id: Int): Flow<MovieDetailsModel> =
        moviesRepository.getMovieById(id)
}