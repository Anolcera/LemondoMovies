package anolcera.lemondomovies.movie_details

import android.util.Log
import androidx.compose.runtime.Stable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import anolcera.lemondomovies.common.DataResult
import anolcera.lemondomovies.common.asResult
import anolcera.lemondomovies.domain.models.MovieDetailsModel
import anolcera.lemondomovies.domain.useCases.AddMovieToFavoritesUseCase
import anolcera.lemondomovies.domain.useCases.GetFavoriteMovieByIdUseCase
import anolcera.lemondomovies.domain.useCases.GetMovieByIdUseCase
import anolcera.lemondomovies.domain.useCases.RemoveMovieFromFavoritesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val getMovieByIdUseCase: GetMovieByIdUseCase,
    private val addMovieToFavoritesUseCase: AddMovieToFavoritesUseCase,
    private val removeMovieFromFavoritesUseCase: RemoveMovieFromFavoritesUseCase,
    private val getFavoriteMovieByIdUseCase: GetFavoriteMovieByIdUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val selectedMovieId = savedStateHandle.get<String>(detailsRouteNavArg)?.toIntOrNull()

    private val _movieDetailsUiState = MutableStateFlow(MovieDetailsUiState())
    val movieDetailsUiState = _movieDetailsUiState.asStateFlow()

    init {
        viewModelScope.launch {

            getMovieByIdUseCase(selectedMovieId!!) //todo remove !!
                .asResult()
                .collect { dataResult ->
                    when (dataResult) {
                        is DataResult.Error -> {
                            Log.d(TAG, "Error Occurred: ${dataResult.exception.message}")
                        }

                        is DataResult.Success -> {
                            _movieDetailsUiState.update {
                                it.copy(
                                    movie = dataResult.data
                                )
                            }

                            isMovieFavorite(dataResult.data)
                        }

                    }
                }
        }
    }

    private fun isMovieFavorite(movie: MovieDetailsModel) {

        viewModelScope.launch {

            _movieDetailsUiState.update {
                it.copy(
                    isFavorite = getFavoriteMovieByIdUseCase(movie.remoteId) != null
                )
            }
        }
    }

    fun toggleMovieFavoriteStatus(isFavorite: Boolean, movie: MovieDetailsModel) {
        viewModelScope.launch {
            if (isFavorite){
                removeMovieFromFavoritesUseCase(movie.remoteId)
            } else addMovieToFavoritesUseCase(movie)


            _movieDetailsUiState.update {
                it.copy(
                    isFavorite = !isFavorite
                )
            }
        }
    }

    @Stable
    data class MovieDetailsUiState(
        val movie: MovieDetailsModel? = null,
        val isFavorite: Boolean = false,
    )

    companion object {
        private const val TAG = "MovieDetailsViewModel"
    }
}