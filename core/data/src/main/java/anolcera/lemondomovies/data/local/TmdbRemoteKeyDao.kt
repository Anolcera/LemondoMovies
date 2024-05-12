package anolcera.lemondomovies.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TmdbRemoteKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRemoteKey(key: TmbdRemoteKeys)

    @Query("select * from TmbdRemoteKeys where id=:key")
    suspend fun getRemoteKey(key: String): TmbdRemoteKeys?

    @Query("delete from TmbdRemoteKeys")
    suspend fun clearAll()
}