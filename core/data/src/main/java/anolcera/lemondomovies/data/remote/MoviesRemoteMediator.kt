package anolcera.lemondomovies.data.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import anolcera.lemondomovies.common.DataResult
import anolcera.lemondomovies.common.asResult
import anolcera.lemondomovies.data.local.LocalMoviesDatabase
import anolcera.lemondomovies.data.local.MovieDetailsEntity
import anolcera.lemondomovies.data.local.TmbdRemoteKeys
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
                LoadType.REFRESH -> {
                    1
                    /*val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextPage?.minus(1) ?: 1*/
                }

                LoadType.PREPEND -> {
                    return MediatorResult.Success(
                        endOfPaginationReached = true
                    )
                    /*val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKeys?.prevPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    prevPage*/
                }

                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.nextPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    nextPage
                }
            }

            var endOfPaginationReached = true

            //1219685 last ones id

            theMovieDBDataSource.fetchMovies(
                page = currentPage
            ).mapResult().asResult().collect { remoteDataFetchResult ->
                when (remoteDataFetchResult) {
                    is DataResult.Error -> {
                        throw Exception(remoteDataFetchResult.exception)
                    }

                    is DataResult.Success -> {

                        val movies = remoteDataFetchResult.data.results

                        localMoviesDatabase.withTransaction {
                            if (loadType == LoadType.REFRESH) {
                                moviesDao.clearAll()
                                tmdbRemoteKeyDao.clearAll()

                            }

                            endOfPaginationReached = movies.isEmpty()
                            val prevPage = if (currentPage == 1) null else currentPage - 1
                            val nextPage = if (endOfPaginationReached) null else currentPage + 1

                            val keys = movies.map { movie ->
                                TmbdRemoteKeys(
                                    id = movie.id,
                                    prevPage = prevPage,
                                    nextPage = nextPage
                                )
                            }

                            tmdbRemoteKeyDao.insertRemoteKeys(keys)
                            moviesDao.insertAll(
                                movies.map { moviesResult ->
                                    moviesResult.toMovieDetailsEntity()
                                }
                            )
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

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, MovieDetailsEntity>
    ): TmbdRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                tmdbRemoteKeyDao.getRemoteKeys(id = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, MovieDetailsEntity>
    ): TmbdRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { movie ->
                tmdbRemoteKeyDao.getRemoteKeys(id = movie.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, MovieDetailsEntity>
    ): TmbdRemoteKeys? {
        return state.pages.lastOrNull {
            it.data.isNotEmpty()
        }?.data?.lastOrNull()
            ?.let { movie ->
                tmdbRemoteKeyDao.getRemoteKeys(id = movie.id)
            }
    }

    companion object {
        private const val REMOTE_KEY_ID = "PAGE_DATA"
    }
}