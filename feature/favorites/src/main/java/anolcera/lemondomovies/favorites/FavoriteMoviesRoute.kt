package anolcera.lemondomovies.favorites

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import anolcera.lemondomovies.domain.models.MovieDetailsModel
import anolcera.lemondomovies.ui.MoviePoster

@Composable
internal fun FavoriteMoviesRoute(
    favoriteMoviesViewModel: FavoriteMoviesViewModel,
) {
    val favoriteMoviesUiState by favoriteMoviesViewModel.movieDetailsUiState.collectAsStateWithLifecycle()

    FavoriteMoviesScreen(
        favoriteMovies = favoriteMoviesUiState.movies
    )
}

@Composable
private fun FavoriteMoviesScreen(
    favoriteMovies: List<MovieDetailsModel>?
) {

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp)
    ) {
        favoriteMovies?.also {
            LazyColumn(
                modifier = Modifier.padding(top = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                items(
                    items = favoriteMovies,
                    key = { favMovie ->
                        favMovie.remoteId
                    }
                ) {
                    MoviePoster(
                        modifier = Modifier
                            .height(300.dp)
                            .fillParentMaxWidth(),
                        movieTitle = it.title,
                        posterUrl = it.posterPath,
                        onPosterClicked = { /*DISABLED*/ }
                    )
                }
            }
        }
    }
}
