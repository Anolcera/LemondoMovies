package anolcera.myapplication.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import anolcera.lemondomovies.movie_collection.movieCollectionGraphRoute
import anolcera.lemondomovies.movie_collection.movieCollectionNavGraph

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
            onGoToDetails = {/*TODO*/},
            onGoToFavorites = {/*TODO*/}
        )
    }
}