package anolcera.lemondomovies.movie_details

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation

const val movieDetailsNavGraphRoute = "movie_details_details_nav_route_pattern"
private const val detailsRoute = "movie_details_route"

fun NavGraphBuilder.movieDetailsNavGraph(
    navController: NavController,
){
    navigation(
        route = movieDetailsNavGraphRoute,
        startDestination = detailsRoute,
    ){
        composable(route = detailsRoute){

        }
    }
}