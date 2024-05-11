package anolcera.lemondomovies.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TmdbRemoteKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRemoteKeys(key: List<TmbdRemoteKeys>)

    @Query("select * from TmbdRemoteKeys where id=:id")
    suspend fun getRemoteKeys(id: Int): TmbdRemoteKeys?

    @Query("delete from TmbdRemoteKeys")
    suspend fun clearAll()
}