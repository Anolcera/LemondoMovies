package anolcera.lemondomovies.movie_collection

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import anolcera.lemondomovies.domain.models.MovieDetailsModel
import anolcera.lemondomovies.movie_collection.ui.MoviePoster

private const val TAG = "MovieCollectionRoute"

@Composable
internal fun MovieCollectionRoute(
    movieCollectionViewModel: MovieCollectionViewModel,
    onFavoritesClicked: () -> Unit,
    onPosterClicked: (id: Int) -> Unit,
) {
    val movieCollectionState = movieCollectionViewModel.movies.collectAsLazyPagingItems()

    MovieCollectionScreen(
        movieCollection = movieCollectionState,
        onFavoritesClicked = onFavoritesClicked,
        onPosterClicked = onPosterClicked,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MovieCollectionScreen(
    movieCollection: LazyPagingItems<MovieDetailsModel>,
    onFavoritesClicked: () -> Unit,
    onPosterClicked: (id: Int) -> Unit,
) {

    LaunchedEffect(key1 = movieCollection.loadState) {
        if (movieCollection.loadState.refresh is LoadState.Error) {
            //todo call viewmodel to throw event
            Log.d(
                TAG, "Error occurred: ${
                    (movieCollection.loadState.refresh as LoadState.Error).error.message
                }"
            )
        }
    }

    Surface(modifier = Modifier.fillMaxSize()) {

        if (movieCollection.loadState.refresh is LoadState.Loading) {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        } else {
            Column(modifier = Modifier.fillMaxSize()) {

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

                LazyColumn(
                    modifier = Modifier.padding(top = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    items(
                        count = movieCollection.itemCount,
                        key = movieCollection.itemKey {
                            it.id
                        },
                    ) { movieIndex ->

                        movieCollection[movieIndex]?.also { movie ->
                            MoviePoster(
                                modifier = Modifier
                                    .fillParentMaxWidth(),
                                movieTitle = movie.title,
                                posterUrl = movie.posterPath,
                                onPosterClicked = { onPosterClicked(movie.id) }
                            )
                        }
                    }

                    item {
                        if (movieCollection.loadState.append is LoadState.Loading) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }
        }

    }
}

