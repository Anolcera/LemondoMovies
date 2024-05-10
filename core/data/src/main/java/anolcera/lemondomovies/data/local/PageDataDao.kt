package anolcera.lemondomovies.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PageDataDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPageData(key: PageData)

    @Query("select * from PageData where id=:key")
    suspend fun getPageData(key: String): PageData?

    @Query("delete from PageData")
    suspend fun clearPageData()
}