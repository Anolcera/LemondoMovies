package anolcera.lemondomovies.data.remote

import anolcera.lemondomovies.data.remote.models.DiscoverMovieResponse
import anolcera.lemondomovies.data.remote.models.MovieDetailsResponse
import anolcera.lemondomovies.domain.models.DiscoverMovieModel
import anolcera.lemondomovies.domain.models.MovieDetailsModel

fun DiscoverMovieResponse.toDomainModel() = DiscoverMovieModel(
    page = page,
    results = results.map { it.toDomainModel() },
    totalPages = totalPages,
    totalResults = totalResults
)

fun MovieDetailsResponse.toDomainModel() = MovieDetailsModel(
    adult = adult,
    backdropPath = backdropPath,
    genreIds = genreIds,
    id = id,
    originalLanguage = originalLanguage,
    originalTitle = originalTitle,
    overview = overview,
    popularity = popularity,
    posterPath = posterPath,
    releaseDate = releaseDate,
    title = title,
    video = video,
    voteAverage = voteAverage,
    voteCount = voteCount
)