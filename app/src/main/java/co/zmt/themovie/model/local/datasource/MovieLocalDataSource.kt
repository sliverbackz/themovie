package co.zmt.themovie.model.local.datasource

import co.zmt.themovie.model.local.db.movie.MovieWithMovieGenre
import co.zmt.themovie.model.local.db.movie.dao.FavoriteMovieDao
import co.zmt.themovie.model.local.db.movie.dao.MovieDao
import co.zmt.themovie.model.local.db.movie.dao.MovieGenreDao
import co.zmt.themovie.model.local.db.movie.dao.MovieGenreIdDao
import co.zmt.themovie.model.local.db.movie.entity.FavoriteMovie
import co.zmt.themovie.model.local.db.movie.entity.Movie
import co.zmt.themovie.model.local.db.movie.entity.MovieGenre
import co.zmt.themovie.model.local.db.movie.entity.MovieGenreId
import kotlinx.coroutines.flow.Flow
import timber.log.Timber
import javax.inject.Inject

class MovieLocalDataSource @Inject constructor(
    private val movieDao: MovieDao,
    private val movieGenreDao: MovieGenreDao,
    private val movieGenreIdDao: MovieGenreIdDao,
    private val favoriteMovieDao: FavoriteMovieDao
) {
    suspend fun bulkMovieGenreInsert(list: List<MovieGenre>) {
        val flag = movieGenreDao.insert(list)
        Timber.i("${flag.size} ${flag[0]}")
    }

    suspend fun bulkMovieInsert(list: List<Movie>) {
        val flag = movieDao.insert(list)
        Timber.i("${flag.size} ${flag[0]}")
    }

    suspend fun bulkMovieGenreIdInsert(list: List<MovieGenreId>) {
        val flag = movieGenreIdDao.insert(list)
    }

    suspend fun getGenreNameById(id: Int): String {
        return movieGenreDao.getGenreName(id)
    }

    suspend fun getAllMovieGenre(): List<MovieGenre> {
        return movieGenreDao.getAllMovieGenre()
    }

    fun getMovieGenreFlow(): Flow<List<MovieGenre>> {
        return movieGenreDao.getAllMovieGenreFlow()
    }

    fun getMovieFlow(): Flow<List<MovieWithMovieGenre>?> {
        return movieDao.getAllMoviesFlow()
    }

    fun getMovies(): List<MovieWithMovieGenre>?{
        return movieDao.getAllMovies()
    }

    fun getLikedMovieFlow() = movieDao.getLikedMovies()

    fun getMovieDetailFlow(id: Int): Flow<MovieWithMovieGenre?> {
        return movieDao.getMovieByIdFlow(id)
    }

    suspend fun getFavoriteMovie(movieId: Int): FavoriteMovie? {
        return favoriteMovieDao.getFavoriteMovieById(movieId)
    }

    suspend fun insertFavoriteMovie(favMovie: FavoriteMovie) {
        favoriteMovieDao.insert(favMovie)
    }

    suspend fun updateFavoriteMovie(favMovie: FavoriteMovie) {
        favoriteMovieDao.update(favMovie)
    }

    suspend fun getMovieDetail(id: Int): MovieWithMovieGenre? {
        return movieDao.getMovieById(id)
    }

}