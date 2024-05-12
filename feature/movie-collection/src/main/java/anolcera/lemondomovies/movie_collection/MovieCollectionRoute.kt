package anolcera.lemondomovies.movie_collection

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import anolcera.lemondomovies.domain.models.MovieDetailsModel
import anolcera.lemondomovies.ui.MoviePoster

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

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
private fun MovieCollectionScreen(
    movieCollection: LazyPagingItems<MovieDetailsModel>,
    onFavoritesClicked: () -> Unit,
    onPosterClicked: (id: Int) -> Unit,
) {

    val pullToRefreshState = rememberPullRefreshState(
        refreshing = movieCollection.loadState.refresh is LoadState.Loading,
        refreshThreshold = 140.dp,
        onRefresh = { movieCollection.refresh() }
    )

    Surface(modifier = Modifier.fillMaxSize()) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .pullRefresh(pullToRefreshState)
        ) {

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
                                id = anolcera.lemondomovies.ui.R.drawable.favorites_topbar_nagiation_icon
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
                    contentType = movieCollection.itemContentType()
                ) { movieIndex ->

                    movieCollection[movieIndex]?.also { movie ->
                        MoviePoster(
                            modifier = Modifier
                                .height(300.dp)
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

