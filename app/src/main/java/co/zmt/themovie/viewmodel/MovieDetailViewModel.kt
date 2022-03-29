package co.zmt.themovie.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import co.zmt.themovie.model.local.db.movie.MovieWithMovieGenre
import co.zmt.themovie.model.local.db.movie.entity.FavoriteMovie
import co.zmt.themovie.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    fun getMovieDetailLiveData(id: Int) = movieRepository.getMovieDetailFlow(id).asLiveData()

    fun getGenreNamesLiveData(id: Int) =
        movieRepository.getMovieGenreNameListByMovieId(id).asLiveData(Dispatchers.IO)

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

    fun getMovieGenreLiveData(id: Int) = movieRepository.getGenreListFlow()


}