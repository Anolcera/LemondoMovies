package anolcera.lemondomovies.movie_collection.ui

import androidx.compose.foundation.clickable
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest

@Composable
internal fun MoviePoster(
    modifier: Modifier = Modifier,
    movieTitle: String,
    posterUrl: String,
    onPosterClicked: () -> Unit,
) {
    Card(
        modifier = modifier
            .clickable { onPosterClicked() }
    ) {
        SubcomposeAsyncImage(
            model = ImageRequest
                .Builder(LocalContext.current)
                .data(posterUrl)
                .crossfade(true)
                .build(),
            contentScale = ContentScale.FillWidth,
            contentDescription = movieTitle
        )
    }
}