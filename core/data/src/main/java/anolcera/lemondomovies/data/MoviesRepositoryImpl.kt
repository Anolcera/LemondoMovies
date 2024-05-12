package anolcera.lemondomovies.data

import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.map
import anolcera.lemondomovies.data.local.LocalMoviesDatabase
import anolcera.lemondomovies.data.local.pagination.MovieDetailsEntity
import anolcera.lemondomovies.domain.models.MovieDetailsModel
import anolcera.lemondomovies.domain.repositories.MoviesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject


class MoviesRepositoryImpl @Inject constructor(
    private val localMoviesDatabase: LocalMoviesDatabase,
    private val pager: Pager<Int, MovieDetailsEntity>,
) : MoviesRepository {

    override fun getMoviesPage(): Flow<PagingData<MovieDetailsModel>> =
        pager.flow.map { pagingData ->
            pagingData.map { movieEntity -> movieEntity.toDomainModel() }
        }

    override fun getMovieById(id: Int): Flow<MovieDetailsModel> = flow {
        val movieEntity = localMoviesDatabase.moviesDao.getMovieById(id) ?: throw IOException()
        emit(movieEntity.toDomainModel())
    }

    override suspend fun addMovieToFavorites(movie: MovieDetailsModel) {
        localMoviesDatabase.favoriteMoviesDao.insertFavoriteMovie(movie.toFavoriteEntityModel())
    }

    override suspend fun removeMovieFromFavorites(remoteId: Int) {
        localMoviesDatabase.favoriteMoviesDao.removeFavorite(remoteId)
    }

    override suspend fun getFavoriteMovies(): List<MovieDetailsModel>? {
        return localMoviesDatabase.favoriteMoviesDao.getFavorites()?.map {
            it.toDomainModel()
        }
    }

    override suspend fun getFavoriteMovieById(remoteId: Int): MovieDetailsModel? {
        return localMoviesDatabase.favoriteMoviesDao.getFavoriteMovieById(remoteId)?.toDomainModel()
    }

}