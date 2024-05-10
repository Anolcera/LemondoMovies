package anolcera.lemondomovies.movie_collection

import android.util.Log
import androidx.compose.runtime.Stable
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
class MovieCollectionViewModel @Inject constructor(
    private val fetchMoviesUseCase: FetchMoviesUseCase
) : ViewModel() {

    private val _movieCollectionUiState = MutableStateFlow(MovieCollectionUiState())
    val movieCollectionUiState = _movieCollectionUiState.asStateFlow()

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
                            _movieCollectionUiState.update {
                                it.copy(
                                    movies = dataResult.data.results
                                )
                            }
                        }

                    }
                }
        }
    }

    @Stable
    data class MovieCollectionUiState(
        val movies: List<MovieDetailsModel> = emptyList()
    )

    companion object {
        private const val TAG = "MovieCollectionViewMode"
    }
}