package anolcera.lemondomovies.favorites

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import anolcera.lemondomovies.domain.models.MovieDetailsModel
import anolcera.lemondomovies.domain.useCases.GetFavoriteMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteMoviesViewModel @Inject constructor(
    private val getFavoriteMoviesUseCase: GetFavoriteMoviesUseCase,
) : ViewModel() {

    private val _movieDetailsUiState = MutableStateFlow(FavoriteMoviesUiState())
    val movieDetailsUiState = _movieDetailsUiState.asStateFlow()

    init {
        viewModelScope.launch {
            _movieDetailsUiState.update {
                it.copy(
                    movies = getFavoriteMoviesUseCase()
                )
            }
        }
    }

    @Stable
    data class FavoriteMoviesUiState(
        val movies: List<MovieDetailsModel>? = null,
    )
}