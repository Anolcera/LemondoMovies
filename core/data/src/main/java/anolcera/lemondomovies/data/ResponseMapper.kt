package anolcera.lemondomovies.data

import anolcera.lemondomovies.data.local.MovieDetailsEntity
import anolcera.lemondomovies.data.remote.models.DiscoverMovieResponse
import anolcera.lemondomovies.data.remote.models.MovieDetailsResponse
import anolcera.lemondomovies.data.remote.models.TMDB_IMAGE_BASE_URL
import anolcera.lemondomovies.domain.models.DiscoverMovieModel
import anolcera.lemondomovies.domain.models.MovieDetailsModel

fun DiscoverMovieResponse.toDomainModel() = DiscoverMovieModel(
    page = page,
    results = results.map { it.toDomainModel() },
    totalPages = totalPages,
    totalResults = totalResults
)

fun MovieDetailsResponse.toDomainModel() = MovieDetailsModel(
    id = id,
    overview = overview,
    posterPath = TMDB_IMAGE_BASE_URL + posterPath,
    releaseDate = releaseDate,
    title = title,
    voteAverage = voteAverage,
)

fun MovieDetailsResponse.toMovieDetailsEntity() = MovieDetailsEntity(
    id = id,
    overview = overview,
    posterPath = TMDB_IMAGE_BASE_URL + posterPath,
    releaseDate = releaseDate,
    title = title,
    voteAverage = voteAverage,
)

fun MovieDetailsEntity.toDomainModel() = MovieDetailsModel(
    id = id,
    overview = overview,
    posterPath = posterPath,
    releaseDate = releaseDate,
    title = title,
    voteAverage = voteAverage
)