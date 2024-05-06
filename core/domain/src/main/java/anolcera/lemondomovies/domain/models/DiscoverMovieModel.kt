package anolcera.lemondomovies.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class DiscoverMovieModel(
    val page: Int,
    val results: List<MovieDetailsModel>,
    val totalPages: Int,
    val totalResults: Int
)