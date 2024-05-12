package anolcera.lemondomovies.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter

@Composable
fun MoviePoster(
    modifier: Modifier = Modifier,
    movieTitle: String,
    posterUrl: String,
    onPosterClicked: () -> Unit,
) {
    val imagePainter = rememberAsyncImagePainter(
        model = posterUrl,
        imageLoader = ImageLoader
            .Builder(LocalContext.current)
            .crossfade(durationMillis = 1000)
            .error(R.drawable.placeholder)
            .placeholder(R.drawable.placeholder)
            .build()
    )

    Card(
        modifier = modifier
            .clickable { onPosterClicked() }
    ) {
        Image(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.CenterHorizontally),
            painter = imagePainter,
            contentDescription = movieTitle,
            contentScale = ContentScale.Fit
        )
    }
}