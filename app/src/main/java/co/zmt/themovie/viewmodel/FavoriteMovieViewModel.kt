package co.zmt.themovie.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import co.zmt.themovie.model.local.db.movie.LikedMovie
import co.zmt.themovie.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteMovieViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {
    val favoriteMovieLiveData: LiveData<List<LikedMovie>?> =
        movieRepository.getLikedMovieFlow().asLiveData()

}