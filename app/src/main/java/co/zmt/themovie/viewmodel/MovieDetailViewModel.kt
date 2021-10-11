package co.zmt.themovie.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import co.zmt.themovie.model.local.db.movie.MovieWithMovieGenre
import co.zmt.themovie.model.local.db.movie.entity.FavoriteMovie
import co.zmt.themovie.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {
    suspend fun getMovieDetail(id: Int): MovieWithMovieGenre? {
        return movieRepository.getMovieDetail(id)
    }

    fun getMovieDetailLiveData(id: Int) =
        movieRepository.getMovieDetailFlow(id).asLiveData()

    suspend fun insertFavoriteMovie(movie: MovieWithMovieGenre, isFav: Boolean) {
        movieRepository.insertFavoriteMovie(
            FavoriteMovie(
                movieId = movie.movie.movieId,
                isFavorite = isFav
            )
        )
    }

    suspend fun getFavoriteMovie(movieId: Int): FavoriteMovie? {
        return movieRepository.getFavoriteMovie(movieId)
    }

}