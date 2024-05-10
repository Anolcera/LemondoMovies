package anolcera.lemondomovies.movie_details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import anolcera.lemondomovies.domain.models.MovieDetailsModel
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest

@Composable
internal fun MovieDetailsRoute(
    movieDetailsViewModel: MovieDetailsViewModel,
){

    val movieDetailsUiState by movieDetailsViewModel.movieDetailsUiState.collectAsStateWithLifecycle()

    MovieDetailsScreen(
        movie = movieDetailsUiState.movie
    )
}

@Composable
private fun MovieDetailsScreen(
    movie: MovieDetailsModel?
){
    Column {
        SubcomposeAsyncImage(
            model = ImageRequest
                .Builder(LocalContext.current)
                .data(movie?.posterPath)
                .crossfade(true)
                .build(),
            contentScale = ContentScale.FillWidth,
            contentDescription = movie?.title
        )

        Text(modifier = Modifier.padding(top=16.dp), text = "Title: ${movie?.title}")
        Text(modifier = Modifier.padding(top=16.dp), text = "Release Date: ${movie?.releaseDate}")
        Text(modifier = Modifier.padding(top=16.dp), text = "Rating: ${movie?.voteAverage}")
        Text(modifier = Modifier.padding(top=16.dp), text = "Overview: ${movie?.overview}")

    }
}