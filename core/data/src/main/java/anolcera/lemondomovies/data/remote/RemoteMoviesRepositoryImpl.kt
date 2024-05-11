package anolcera.lemondomovies.data.remote

import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.map
import anolcera.lemondomovies.data.local.MovieDetailsEntity
import anolcera.lemondomovies.data.toDomainModel
import anolcera.lemondomovies.domain.models.MovieDetailsModel
import anolcera.lemondomovies.domain.repositories.RemoteMoviesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class RemoteMoviesRepositoryImpl @Inject constructor(
    private val theMovieDBDataSource: TheMovieDBDataSource,
    private val pager: Pager<Int, MovieDetailsEntity>,
) : RemoteMoviesRepository {
    /*override suspend fun fetchMovies(): Flow<DiscoverMovieModel> {
        return theMovieDBDataSource.fetchMovies().mapResult().map {
            it.toDomainModel()
        }
    }*/

    override fun getMoviesPage(): Flow<PagingData<MovieDetailsModel>> =
        pager.flow.map { pagingData ->
            pagingData.map { movieEntity -> movieEntity.toDomainModel() }
        }
}