package co.zmt.themovie.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import co.zmt.themovie.helper.AsyncViewStateLiveData
import co.zmt.themovie.model.local.db.movie.entity.MovieGenre
import co.zmt.themovie.repository.AsyncResource
import co.zmt.themovie.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val movieGenreStateLiveData = AsyncViewStateLiveData<Unit>()

    val movieGenreLiveData: LiveData<List<MovieGenre>> = movieRepository.getGenreListFlow().asLiveData()

    fun getMovieGenres() {
        viewModelScope.launch(Dispatchers.IO) {
            movieGenreStateLiveData.postLoading()
            val result = movieRepository.getGenreList()
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