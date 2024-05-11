package anolcera.lemondomovies.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<MovieDetailsEntity>)

    @Query("SELECT * FROM MovieDetailsEntity")
    fun pagingSource(): PagingSource<Int, MovieDetailsEntity>

    @Query("DELETE FROM MovieDetailsEntity")
    suspend fun clearAll()
}