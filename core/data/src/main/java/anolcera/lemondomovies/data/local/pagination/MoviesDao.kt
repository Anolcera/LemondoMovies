package anolcera.lemondomovies.data.local.pagination

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
    fun getAllMovies(): PagingSource<Int, MovieDetailsEntity>

    @Query("SELECT * FROM MovieDetailsEntity WHERE  id=:id")
    suspend fun getMovieById(id: Int): MovieDetailsEntity?

    @Query("DELETE FROM MovieDetailsEntity")
    suspend fun clearAll()
}