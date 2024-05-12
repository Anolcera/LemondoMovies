package anolcera.lemondomovies.favorites

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation

const val favoriteMoviesNavGraphPattern = "favorite_movies_nav_graph_pattern"
private const val favoriteMoviesRoute = "favorite_movies_nav_route"

fun NavGraphBuilder.favoriteMoviesNavGraph() {
    navigation(
        route = favoriteMoviesNavGraphPattern,
        startDestination = favoriteMoviesRoute
    ) {
        composable(route = favoriteMoviesRoute){
            FavoriteMoviesRoute(favoriteMoviesViewModel = hiltViewModel())
        }
    }
}