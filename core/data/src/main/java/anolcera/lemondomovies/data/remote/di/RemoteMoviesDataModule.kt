package anolcera.lemondomovies.data.remote.di

import anolcera.lemondomovies.data.MoviesRepositoryImpl
import anolcera.lemondomovies.data.remote.TheMovieDBDataSource
import anolcera.lemondomovies.domain.repositories.MoviesRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteMoviesDataModule {

    @Binds
    abstract fun bindRemoteMoviesRepository(
        remoteMoviesRepositoryImpl: MoviesRepositoryImpl
    ): MoviesRepository

    companion object {
        @Provides
        @Singleton
        fun provideTheMovieDBDataSource(retrofit: Retrofit): TheMovieDBDataSource {
            return retrofit.create(TheMovieDBDataSource::class.java)
        }
    }
}