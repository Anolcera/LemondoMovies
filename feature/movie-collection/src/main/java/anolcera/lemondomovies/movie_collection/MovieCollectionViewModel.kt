package anolcera.lemondomovies.movie_collection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import anolcera.lemondomovies.domain.useCases.GetMoviesPagingDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieCollectionViewModel @Inject constructor(
    getMoviesPagingData: GetMoviesPagingDataUseCase
) : ViewModel() {

    val movies = getMoviesPagingData()
        .cachedIn(viewModelScope)
}