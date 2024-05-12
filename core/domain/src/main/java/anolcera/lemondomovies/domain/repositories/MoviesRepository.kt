package anolcera.lemondomovies.domain.repositories

import androidx.paging.PagingData
import anolcera.lemondomovies.domain.models.MovieDetailsModel
import kotlinx.coroutines.flow.Flow


interface MoviesRepository {

    fun getMoviesPage(): Flow<PagingData<MovieDetailsModel>>

    fun getMovieById(id: Int): Flow<MovieDetailsModel>

    suspend fun addMovieToFavorites(movie: MovieDetailsModel)

    suspend fun removeMovieFromFavorites(remoteId: Int)

    suspend fun getFavoriteMovies(): List<MovieDetailsModel>?

    suspend fun getFavoriteMovieById(remoteId: Int): MovieDetailsModel?
}