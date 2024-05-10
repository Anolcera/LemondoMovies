package anolcera.lemondomovies.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface MoviesDao {

    @Upsert
    suspend fun upsertAll(movies: List<MovieDetailsEntity>)

    @Query("SELECT * FROM MovieDetailsEntity")
    fun pagingSource(): PagingSource<Int, MovieDetailsEntity>

    @Query("DELETE FROM MovieDetailsEntity")
    suspend fun clearAll()
}