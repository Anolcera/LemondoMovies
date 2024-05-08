package anolcera.lemondomovies.movie_collection

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation

const val movieCollectionGraphRoute = "movie_collection_details_nav_route_pattern"
private const val detailsRoute = "movie_collection_details_route"


fun NavGraphBuilder.movieCollectionNavGraph(
    navController: NavController,
    onGoToDetails: () -> Unit,
    onGoToFavorites: () -> Unit,
){
    navigation(
        route = movieCollectionGraphRoute,
        startDestination = detailsRoute
    ){
        composable(route = detailsRoute){
            MovieCollectionRoute(
                movieCollectionViewModel = hiltViewModel()
            )
        }
    }
}