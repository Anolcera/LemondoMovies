package anolcera.lemondomovies.movie_collection

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import anolcera.lemondomovies.domain.models.MovieDetailsModel
import anolcera.lemondomovies.movie_collection.ui.MoviePoster

@Composable
internal fun MovieCollectionRoute(
    movieCollectionViewModel: MovieCollectionViewModel,
    onFavoritesClicked: () -> Unit,
    onPosterClicked: (id: Int) -> Unit,
) {
    val movieCollectionState by movieCollectionViewModel.movieCollectionUiState.collectAsStateWithLifecycle()

    MovieCollectionScreen(
        movieCollection = movieCollectionState.movies,
        onFavoritesClicked = onFavoritesClicked,
        onPosterClicked = onPosterClicked,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MovieCollectionScreen(
    movieCollection: List<MovieDetailsModel>,
    onFavoritesClicked: () -> Unit,
    onPosterClicked: (id: Int) -> Unit,
) {

    Surface(modifier = Modifier.fillMaxSize()) {
        Column {

            TopAppBar(
                modifier = Modifier
                    .height(56.dp)
                    .padding(top = 8.dp),
                title = {
                    Text(
                        modifier = Modifier.fillMaxSize(),
                        text = stringResource(R.string.movie_collection_screen_title),
                        textAlign = TextAlign.Center
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onFavoritesClicked) {
                        Icon(
                            painter = painterResource(
                                id = R.drawable.favorites_topbar_nagiation_icon
                            ),
                            contentDescription = null
                        )
                    }
                }
            )

            LazyColumn {
                items(
                    items = movieCollection,
                    key = { it.id }
                ) { movie ->
                    MoviePoster(
                        modifier = Modifier
                            .fillParentMaxWidth()
                            .padding(all = 16.dp),
                        movieTitle = movie.title,
                        posterUrl = movie.posterPath,
                        onPosterClicked = { onPosterClicked(movie.id) }
                    )
                }

            }
        }
    }
}

