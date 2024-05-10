package anolcera.lemondomovies.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [MovieDetailsEntity::class],
    version = 1
)
abstract class LocalMoviesDatabase: RoomDatabase() {

    abstract  val moviesDao: MoviesDao

    abstract fun pageDataDao() : PageDataDao
}