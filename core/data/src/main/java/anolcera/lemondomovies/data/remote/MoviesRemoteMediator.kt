package anolcera.lemondomovies.data.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import anolcera.lemondomovies.common.DataResult
import anolcera.lemondomovies.common.asResult
import anolcera.lemondomovies.data.local.LocalMoviesDatabase
import anolcera.lemondomovies.data.local.pagination.MovieDetailsEntity
import anolcera.lemondomovies.data.local.pagination.TmbdRemoteKeys
import anolcera.lemondomovies.data.toMovieDetailsEntity
import anolcera.lemondomovies.network.mapResult
import okio.IOException
import retrofit2.HttpException

@OptIn(ExperimentalPagingApi::class)
class MoviesRemoteMediator(
    private val localMoviesDatabase: LocalMoviesDatabase,
    private val theMovieDBDataSource: TheMovieDBDataSource,
) : RemoteMediator<Int, MovieDetailsEntity>() {

    private val moviesDao = localMoviesDatabase.moviesDao
    private val tmdbRemoteKeyDao = localMoviesDatabase.tmdbRemoteKeyDao

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieDetailsEntity>
    ): MediatorResult {
        return try {

            val currentPage = when (loadType) {
                LoadType.REFRESH -> { 1 }

                LoadType.PREPEND -> {
                    return MediatorResult.Success(true)
                }

                LoadType.APPEND -> {
                    val remoteKey = localMoviesDatabase.withTransaction {
                        tmdbRemoteKeyDao.getRemoteKey(REMOTE_KEY_ID)
                    } ?: return MediatorResult.Success(true)

                    if(remoteKey.nextPage == null) {
                        return MediatorResult.Success(true)
                    }

                    remoteKey.nextPage
                }
            }

            var endOfPaginationReached = true

            theMovieDBDataSource.fetchMovies(
                page = currentPage
            ).mapResult().asResult().collect { remoteDataFetchResult ->
                when (remoteDataFetchResult) {
                    is DataResult.Error -> {
                        endOfPaginationReached = true
                    }

                    is DataResult.Success -> {

                        val movies = remoteDataFetchResult.data.results.map { moviesResult ->
                            moviesResult.toMovieDetailsEntity()
                        }

                        localMoviesDatabase.withTransaction {
                            if (loadType == LoadType.REFRESH) {
                                moviesDao.clearAll()
                                tmdbRemoteKeyDao.clearAll()

                            }

                            endOfPaginationReached = false
                            val nextPage = currentPage + 1

                            tmdbRemoteKeyDao.insertRemoteKey(
                                TmbdRemoteKeys(
                                    id = REMOTE_KEY_ID,
                                    nextPage = nextPage,
                                )
                            )
                            moviesDao.insertAll(movies)
                        }
                    }
                }
            }

            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)

        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

    companion object {
        private const val REMOTE_KEY_ID = "PAGE_DATA"
    }
}