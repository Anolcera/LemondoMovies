package anolcera.lemondomovies.movie_details

import android.util.Log
import androidx.compose.runtime.Stable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import anolcera.lemondomovies.common.DataResult
import anolcera.lemondomovies.common.asResult
import anolcera.lemondomovies.domain.models.MovieDetailsModel
import anolcera.lemondomovies.domain.useCases.FetchMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val fetchMoviesUseCase: FetchMoviesUseCase,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val selectedMovieId = savedStateHandle.get<String>(detailsRouteNavArg)?.toIntOrNull()

    private val _movieDetailsUiState = MutableStateFlow(MovieDetailsUiState())
    val movieDetailsUiState = _movieDetailsUiState.asStateFlow()
    init {
        viewModelScope.launch {
            fetchMoviesUseCase()
                .asResult()
                .collect { dataResult ->
                    when (dataResult) {
                        is DataResult.Error -> {
                            Log.d(TAG, "Error Occurred: ${dataResult.exception.message}")
                        }

                        is DataResult.Success -> {
                            _movieDetailsUiState.update {
                                it.copy(
                                    movie = dataResult.data.results.firstOrNull { movie ->
                                        movie.id == selectedMovieId
                                    }
                                )
                            }
                        }

                    }
                }
        }
    }


    @Stable
    data class MovieDetailsUiState(
        val movie: MovieDetailsModel? = null,
    )

    companion object {
        private const val TAG = "MovieDetailsViewModel"
    }
}