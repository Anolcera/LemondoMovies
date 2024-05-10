package anolcera.myapplication.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import anolcera.lemondomovies.movie_collection.movieCollectionGraphRoute
import anolcera.lemondomovies.movie_collection.movieCollectionNavGraph
import anolcera.lemondomovies.movie_details.movieDetailsNavGraph
import anolcera.lemondomovies.movie_details.navigateToMovieDetails

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = movieCollectionGraphRoute
    ) {
        movieCollectionNavGraph(
            onGoToDetails = { movieId ->
                navController.navigateToMovieDetails(movieId)
            },
            onGoToFavorites = {/*TODO*/ }
        )

        movieDetailsNavGraph()
    }
}