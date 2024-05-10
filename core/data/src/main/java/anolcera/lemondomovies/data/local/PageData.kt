package anolcera.lemondomovies.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PageData(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val nextPage: Int?,
    val lastUpdated: Long
)
