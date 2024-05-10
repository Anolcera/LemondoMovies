package anolcera.lemondomovies.movie_details

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation

internal const val detailsRouteNavArg = "movieId"

private const val movieDetailsNavGraphRoute = "movie_details_details_nav_route_pattern"
private const val detailsRoute = "movie_details_route/{$detailsRouteNavArg}"

fun NavController.navigateToMovieDetails(movieId: Int){
    this.navigate(detailsRoute.replace("{$detailsRouteNavArg}", movieId.toString()))
}

fun NavGraphBuilder.movieDetailsNavGraph() {
    navigation(
        route = movieDetailsNavGraphRoute,
        startDestination = detailsRoute,
    ) {
        composable(
            route = detailsRoute,
            arguments = listOf(navArgument(detailsRouteNavArg){
                NavType.IntType
            })
        ) {
            MovieDetailsRoute(
                movieDetailsViewModel = hiltViewModel(),
            )
        }
    }
}