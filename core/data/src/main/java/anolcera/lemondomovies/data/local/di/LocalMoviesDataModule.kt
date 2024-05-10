package anolcera.lemondomovies.data.local.di

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Room
import anolcera.lemondomovies.data.local.LocalMoviesDatabase
import anolcera.lemondomovies.data.local.MovieDetailsEntity
import anolcera.lemondomovies.data.remote.MoviesRemoteMediator
import anolcera.lemondomovies.data.remote.TheMovieDBDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalMoviesDataModule {

    @Provides
    @Singleton
    fun provideLocalMovieDatabase(@ApplicationContext context: Context): LocalMoviesDatabase {
        return Room.databaseBuilder(
            context,
            LocalMoviesDatabase::class.java,
            "local_movies_database.db"
        ).build()
    }

    @OptIn(ExperimentalPagingApi::class)
    @Provides
    @Singleton
    fun provideMoviePager(
        localMoviesDatabase: LocalMoviesDatabase,
        theMovieDBDataSource: TheMovieDBDataSource,
    ): Pager<Int, MovieDetailsEntity> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = MoviesRemoteMediator(
                localMoviesDatabase = localMoviesDatabase,
                theMovieDBDataSource = theMovieDBDataSource,
            ),
            pagingSourceFactory = {
                localMoviesDatabase.moviesDao.pagingSource()
            }
        )
    }
}