package anolcera.lemondomovies.data

import anolcera.lemondomovies.data.local.favorites.FavoriteMovieDetailsEntity
import anolcera.lemondomovies.data.local.pagination.MovieDetailsEntity
import anolcera.lemondomovies.data.remote.models.MovieDetailsResponse
import anolcera.lemondomovies.data.remote.models.TMDB_IMAGE_BASE_URL
import anolcera.lemondomovies.domain.models.MovieDetailsModel

fun MovieDetailsResponse.toMovieDetailsEntity() = MovieDetailsEntity(
    overview = overview,
    posterPath = TMDB_IMAGE_BASE_URL + posterPath,
    releaseDate = releaseDate,
    title = title,
    voteAverage = voteAverage,
    remoteId = id,
)

fun MovieDetailsEntity.toDomainModel() = MovieDetailsModel(
    id = id,
    overview = overview,
    posterPath = posterPath,
    releaseDate = releaseDate,
    title = title,
    voteAverage = voteAverage,
    remoteId = remoteId,
)

fun MovieDetailsModel.toFavoriteEntityModel() = FavoriteMovieDetailsEntity(
    remoteId = remoteId,
    overview = overview,
    posterPath = posterPath,
    releaseDate = releaseDate,
    title = title,
    voteAverage = voteAverage
)

fun FavoriteMovieDetailsEntity.toDomainModel() = MovieDetailsModel(
    remoteId = remoteId,
    overview = overview,
    posterPath = posterPath,
    releaseDate = releaseDate,
    title = title,
    voteAverage = voteAverage
)