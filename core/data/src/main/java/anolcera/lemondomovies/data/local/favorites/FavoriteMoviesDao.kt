package anolcera.lemondomovies.data.local.favorites

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import anolcera.lemondomovies.data.local.pagination.MovieDetailsEntity

@Dao
interface FavoriteMoviesDao {

    @Upsert
    suspend fun insertFavoriteMovie(movie: FavoriteMovieDetailsEntity)

    @Query("SELECT * FROM FavoriteMovieDetailsEntity")
    suspend fun getFavorites(): List<FavoriteMovieDetailsEntity>?

    @Query("SELECT * FROM FavoriteMovieDetailsEntity WHERE remoteId=:remoteId")
    suspend fun getFavoriteMovieById(remoteId: Int): FavoriteMovieDetailsEntity?

    @Query("DELETE FROM FavoriteMovieDetailsEntity WHERE remoteId=:remoteId")
    suspend fun removeFavorite(remoteId: Int)

    @Query("DELETE FROM FavoriteMovieDetailsEntity")
    suspend fun clearAll()
}