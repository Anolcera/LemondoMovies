package anolcera.lemondomovies.movie_collection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import anolcera.lemondomovies.domain.useCases.GetMoviesPagingData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieCollectionViewModel @Inject constructor(
    getMoviesPagingData: GetMoviesPagingData
) : ViewModel() {

    val movies = getMoviesPagingData()
        .cachedIn(viewModelScope)

    /*private var _movieCollectionUiState = MutableStateFlow(MovieCollectionUiState())
    val movieCollectionUiState = _movieCollectionUiState.asStateFlow()

    init {
        viewModelScope.launch {
            getMoviesPagingData()
                .cachedIn(viewModelScope)
                .asResult()
                .collect { dataResult ->
                    when (dataResult) {
                        is DataResult.Error -> {
                            Log.d(TAG, "Error Occurred: ${dataResult.exception.message}")
                        }

                        is DataResult.Success -> {
                            _movieCollectionUiState.update {
                                it.copy(
                                    movies = dataResult.data
                                )
                            }
                        }

                    }
                }
        }
    }

    @Stable
    data class MovieCollectionUiState(
        val movies: PagingData.Companion = PagingData.Companion
    )*/

    companion object {
        private const val TAG = "MovieCollectionViewMode"
    }
}