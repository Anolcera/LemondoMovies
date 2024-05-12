package anolcera.lemondomovies.movie_details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import anolcera.lemondomovies.domain.models.MovieDetailsModel
import anolcera.lemondomovies.domain.useCases.RemoveMovieFromFavoritesUseCase
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import javax.annotation.Untainted

@Composable
internal fun MovieDetailsRoute(
    movieDetailsViewModel: MovieDetailsViewModel,
){

    val movieDetailsUiState by movieDetailsViewModel.movieDetailsUiState.collectAsStateWithLifecycle()

    MovieDetailsScreen(
        movie = movieDetailsUiState.movie,
        isMovieFavorite = movieDetailsUiState.isFavorite,
        toggleFavoriteStatus = movieDetailsViewModel::toggleMovieFavoriteStatus,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MovieDetailsScreen(
    movie: MovieDetailsModel?,
    isMovieFavorite: Boolean,
    toggleFavoriteStatus: (Boolean, MovieDetailsModel) -> Unit,
){

    val isFavorite by remember(isMovieFavorite) {
        mutableStateOf(isMovieFavorite)
    }

    Surface{
        Column {
            TopAppBar(
                modifier = Modifier
                    .height(56.dp)
                    .padding(vertical = 8.dp),
                title = {

                },
                actions = {
                    IconButton(onClick = {
                        movie?.also {
                            toggleFavoriteStatus(isFavorite, it)
                        }
                    }) {
                        Icon(
                            painter = painterResource(
                                id = if (isFavorite){
                                    R.drawable.favorite_filled_topbar_action_icon
                                } else R.drawable.favorite_topbar_action_icon
                            ),
                            contentDescription = null
                        )
                    }
                },
            )

            Column(
                modifier = Modifier.verticalScroll(rememberScrollState())
            ) {
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

                Spacer(modifier = Modifier.padding(bottom = 32.dp))
            }
        }
    }
}