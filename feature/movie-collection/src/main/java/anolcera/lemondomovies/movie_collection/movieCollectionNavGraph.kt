package anolcera.lemondomovies.movie_collection

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation

const val movieCollectionGraphRoute = "movie_collection_details_nav_route_pattern"
private const val collectionRoute = "movie_collection_route"


fun NavGraphBuilder.movieCollectionNavGraph(
    onGoToDetails: (id: Int) -> Unit,
    onGoToFavorites: () -> Unit,
){
    navigation(
        route = movieCollectionGraphRoute,
        startDestination = collectionRoute
    ){
        composable(route = collectionRoute){
            MovieCollectionRoute(
                movieCollectionViewModel = hiltViewModel(),
                onFavoritesClicked = onGoToFavorites,
                onPosterClicked = onGoToDetails
            )
        }
    }
}