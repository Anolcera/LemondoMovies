package anolcera.lemondomovies.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface PageDataDao {
    @Upsert
    suspend fun upsertPageData(key: PageDataEntity)

    @Query("select * from PageData where id=:key")
    suspend fun getPageData(key: String): PageDataEntity?

    @Query("delete from PageData")
    suspend fun clearPageData()
}