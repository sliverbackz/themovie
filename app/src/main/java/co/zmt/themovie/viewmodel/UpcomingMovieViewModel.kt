package co.zmt.themovie.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.zmt.themovie.helper.AsyncViewStateLiveData
import co.zmt.themovie.helper.State
import co.zmt.themovie.helper.UiState
import co.zmt.themovie.model.local.db.movie.MovieWithMovieGenre
import co.zmt.themovie.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpcomingMovieViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {
    val movieStateLiveData = AsyncViewStateLiveData<List<MovieWithMovieGenre>?>()
    private var _movieUiStateFlow =
        MutableStateFlow<UiState<List<MovieWithMovieGenre>?>>(
            UiState.Success(
                emptyList()
            )
        )

    val movieUiStateFlow:
            StateFlow<UiState<List<MovieWithMovieGenre>?>> = _movieUiStateFlow

    fun fetchUpcomingMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            movieRepository.fetchUpcomingMovie()
                .collect {
                    when (it) {
                        is State.Start -> {
                            //localdata
                            movieStateLiveData.postSuccess(it.value)
                        }
                        is State.Error -> {
                            movieStateLiveData.postError(it.exception, it.errorMessage)
                            _movieUiStateFlow.value = UiState.Error(it.exception, it.errorMessage)
                        }
                        is State.Loading -> {
                            movieStateLiveData.postLoading()
                            _movieUiStateFlow.value = UiState.Loading()
                        }
                        is State.Success -> {
                            movieStateLiveData.postSuccess(it.value)
                            _movieUiStateFlow.value =
                                UiState.Success(it.value)
                        }
                    }
                }
        }
    }

}