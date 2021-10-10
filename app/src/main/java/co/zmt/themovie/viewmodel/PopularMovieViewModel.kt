package co.zmt.themovie.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import co.zmt.themovie.model.local.db.movie.MovieWithMovieGenre
import co.zmt.themovie.repository.AsyncResource
import co.zmt.themovie.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class PopularMovieViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    val movieLiveData: LiveData<List<MovieWithMovieGenre>?> =
        movieRepository.getPopularMoviesFlow().asLiveData()

    fun getPopularMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = movieRepository.getPopularMovies()
            when (result) {
                is AsyncResource.Error -> {
                    Timber.i(result.errorMessage)
                }
                is AsyncResource.Loading -> {
                    Timber.i("Loading")
                }
                is AsyncResource.Success -> {
                    Timber.i(result.value?.size.toString())
                }
            }
        }
    }
}