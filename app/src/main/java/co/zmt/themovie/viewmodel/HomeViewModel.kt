package co.zmt.themovie.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.zmt.themovie.helper.AsyncViewStateLiveData
import co.zmt.themovie.helper.State
import co.zmt.themovie.model.local.db.movie.entity.MovieGenre
import co.zmt.themovie.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val movieGenreStateLiveData = AsyncViewStateLiveData<List<MovieGenre>?>()

    fun getMovieGenres() {
        viewModelScope.launch(Dispatchers.IO) {
            movieRepository.fetchGenreList().collect {
                when (it) {
                    is State.Start -> {
                        movieGenreStateLiveData.postSuccess(it.value)
                    }
                    is State.Error -> {
                        movieGenreStateLiveData.postError(it.exception, it.errorMessage)
                    }
                    is State.Loading -> {
                        movieGenreStateLiveData.postLoading()
                    }
                    is State.Success -> {
                        movieGenreStateLiveData.postSuccess(it.value)
                    }
                }
            }
        }
    }

}