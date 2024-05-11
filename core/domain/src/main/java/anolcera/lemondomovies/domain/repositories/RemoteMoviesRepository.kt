package anolcera.lemondomovies.domain.repositories

import androidx.paging.PagingData
import anolcera.lemondomovies.domain.models.MovieDetailsModel
import kotlinx.coroutines.flow.Flow


interface RemoteMoviesRepository {

    //suspend fun getMovieById(): Flow<DiscoverMovieModel>

    fun getMoviesPage(): Flow<PagingData<MovieDetailsModel>>
}