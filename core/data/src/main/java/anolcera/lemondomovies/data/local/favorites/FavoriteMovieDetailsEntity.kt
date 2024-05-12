package anolcera.lemondomovies.data.local.favorites

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavoriteMovieDetailsEntity(
    @PrimaryKey(autoGenerate = false)
    val remoteId: Int,
    val overview: String,
    val posterPath: String,
    val releaseDate: String,
    val title: String,
    val voteAverage: Double,
)