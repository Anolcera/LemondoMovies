package anolcera.lemondomovies.domain.useCases

import androidx.paging.PagingData
import anolcera.lemondomovies.domain.models.MovieDetailsModel
import anolcera.lemondomovies.domain.repositories.MoviesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMoviesPagingDataUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) {
    operator fun invoke(): Flow<PagingData<MovieDetailsModel>> =
        moviesRepository.getMoviesPage()
}
