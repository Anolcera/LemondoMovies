package anolcera.lemondomovies.domain.useCases

import androidx.paging.PagingData
import anolcera.lemondomovies.domain.models.MovieDetailsModel
import anolcera.lemondomovies.domain.repositories.RemoteMoviesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMoviesPagingData @Inject constructor(
    private val remoteMoviesRepository: RemoteMoviesRepository
) {
    suspend operator fun invoke(): Flow<PagingData<MovieDetailsModel>> =
        remoteMoviesRepository.getMoviesPage()
}
