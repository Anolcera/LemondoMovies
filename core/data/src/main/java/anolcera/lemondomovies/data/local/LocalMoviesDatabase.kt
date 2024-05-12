package anolcera.lemondomovies.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import anolcera.lemondomovies.data.local.favorites.FavoriteMovieDetailsEntity
import anolcera.lemondomovies.data.local.favorites.FavoriteMoviesDao
import anolcera.lemondomovies.data.local.pagination.MovieDetailsEntity
import anolcera.lemondomovies.data.local.pagination.MoviesDao
import anolcera.lemondomovies.data.local.pagination.TmbdRemoteKeys
import anolcera.lemondomovies.data.local.pagination.TmdbRemoteKeyDao

@Database(
    entities = [
        MovieDetailsEntity::class,
        TmbdRemoteKeys::class,
        FavoriteMovieDetailsEntity::class,
    ],
    version = 1
)
abstract class LocalMoviesDatabase : RoomDatabase() {

    abstract val moviesDao: MoviesDao

    abstract val tmdbRemoteKeyDao: TmdbRemoteKeyDao

    abstract val favoriteMoviesDao: FavoriteMoviesDao
}