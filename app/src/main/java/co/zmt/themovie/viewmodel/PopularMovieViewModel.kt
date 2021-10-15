package co.zmt.themovie.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import co.zmt.themovie.helper.AsyncViewStateLiveData
import co.zmt.themovie.helper.State
import co.zmt.themovie.model.local.db.movie.MovieWithMovieGenre
import co.zmt.themovie.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PopularMovieViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    val movieLiveData: LiveData<List<MovieWithMovieGenre>?> =
        movieRepository.getPopularMoviesFlow().asLiveData()

    val movieStateLiveData = AsyncViewStateLiveData<List<MovieWithMovieGenre>?>()

    fun getPopularMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            movieRepository.fetchPopularMovies().collect {
                when (it) {
                    is State.Start -> {
                        movieStateLiveData.postSuccess(it.value)
                    }
                    is State.Error -> {
                        movieStateLiveData.postError(it.exception, it.errorMessage)
                    }
                    is State.Loading -> {
                        movieStateLiveData.postLoading()
                    }
                    is State.Success -> {
                        movieStateLiveData.postSuccess(it.value)
                    }
                }
            }

        }
    }
}