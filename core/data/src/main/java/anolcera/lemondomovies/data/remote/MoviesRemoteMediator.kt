package anolcera.lemondomovies.data.remote

import android.provider.MediaStore.Audio.Media
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import anolcera.lemondomovies.common.DataResult
import anolcera.lemondomovies.common.asResult
import anolcera.lemondomovies.data.local.LocalMoviesDatabase
import anolcera.lemondomovies.data.local.MovieDetailsEntity
import anolcera.lemondomovies.data.toMovieDetailsEntity
import anolcera.lemondomovies.network.mapResult
import okio.IOException
import retrofit2.HttpException
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalPagingApi::class)
class MoviesRemoteMediator(
    private val localMoviesDatabase: LocalMoviesDatabase,
    private val theMovieDBDataSource: TheMovieDBDataSource,
) : RemoteMediator<Int, MovieDetailsEntity>() {

    override suspend fun initialize(): InitializeAction {
        val remoteKey = localMoviesDatabase.withTransaction {
            localMoviesDatabase.pageDataDao().getPageData(REMOTE_KEY_ID)
        } ?: return InitializeAction.LAUNCH_INITIAL_REFRESH

        val cacheTimeout = TimeUnit.HOURS.convert(1, TimeUnit.MILLISECONDS)

        return if ((System.currentTimeMillis() - remoteKey.lastUpdated) >= cacheTimeout) {
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieDetailsEntity>
    ): MediatorResult {
        return try {

            val loadKey = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(
                    endOfPaginationReached = true
                )

                LoadType.APPEND -> {
                    val remoteKey = localMoviesDatabase.withTransaction {
                        localMoviesDatabase.pageDataDao().getPageData(REMOTE_KEY_ID)
                    } ?: return MediatorResult.Success(true)

                    if (remoteKey.nextPage == null) {
                        return MediatorResult.Success(true)
                    }

                    remoteKey.nextPage
                }
            }

            var endOfPaginationReached = true
            val movies = theMovieDBDataSource.fetchMovies(
                page = loadKey
            ).mapResult().asResult().collect { remoteDataFetchResult ->
                when (remoteDataFetchResult) {
                    is DataResult.Error -> {
                        throw Exception(remoteDataFetchResult.exception)
                    }

                    is DataResult.Success -> {

                        val movies = remoteDataFetchResult.data

                        localMoviesDatabase.withTransaction {
                            if (loadType == LoadType.REFRESH) {
                                localMoviesDatabase.moviesDao.clearAll()
                            }

                            val nextLoadKey = if (movies.results.isEmpty()) {
                                null
                            } else {
                                loadKey + 1
                            }

                            val movieEntities = movies.results.map { moviesResult ->
                                moviesResult.toMovieDetailsEntity()
                            }

                            localMoviesDatabase.moviesDao.upsertAll(movieEntities)
                            endOfPaginationReached = movies.results.isEmpty()
                        }
                    }
                }
            }

            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)

        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        } catch (e: Exception){
            MediatorResult.Error(e)
        }
    }

    companion object {
        private const val REMOTE_KEY_ID = "PAGE_DATA"
    }
}