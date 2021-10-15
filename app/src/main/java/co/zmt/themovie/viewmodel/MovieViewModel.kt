package co.zmt.themovie.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import co.zmt.themovie.helper.doWithException
import co.zmt.themovie.model.local.db.movie.entity.MovieGenre
import co.zmt.themovie.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    val movieGenreLiveData: LiveData<List<MovieGenre>> =
        movieRepository.getGenreListFlow().asLiveData()

    fun getMovieGenres() {
        viewModelScope.launch(Dispatchers.IO) {
            val runCatcher = runCatching {
                movieRepository.fetchGenreList()
            }
            runCatcher.doWithException {}
        }
    }

}