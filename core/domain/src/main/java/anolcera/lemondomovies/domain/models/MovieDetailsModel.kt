package anolcera.lemondomovies.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailsModel(
    val id: Int = 0,
    val remoteId: Int,
    val overview: String,
    val posterPath: String,
    val releaseDate: String,
    val title: String,
    val voteAverage: Double,
)
