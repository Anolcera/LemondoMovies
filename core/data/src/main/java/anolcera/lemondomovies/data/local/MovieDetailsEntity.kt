package anolcera.lemondomovies.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieDetailsEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val overview: String,
    val posterPath: String,
    val releaseDate: String,
    val title: String,
    val voteAverage: Double,
)