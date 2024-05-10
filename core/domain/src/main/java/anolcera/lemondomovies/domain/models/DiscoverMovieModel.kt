package anolcera.lemondomovies.domain.models

import javax.annotation.concurrent.Immutable

@Immutable
data class DiscoverMovieModel(
    val page: Int,
    val results: List<MovieDetailsModel>,
    val totalPages: Int,
    val totalResults: Int
)