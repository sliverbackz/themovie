package co.zmt.themovie.model.local.db.movie.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import co.zmt.themovie.model.local.db.movie.LikedMovie
import co.zmt.themovie.model.local.db.movie.MovieWithMovieGenre
import co.zmt.themovie.model.local.db.movie.entity.Movie
import kotlinx.coroutines.flow.Flow

@Dao
abstract class MovieDao : BaseDao<Movie> {
    @Transaction
    @Query("select * from movie")
    abstract fun getAllMoviesFlow(): Flow<List<MovieWithMovieGenre>?>

    @Transaction
    @Query("select * from movie")
    abstract fun getAllMovies(): List<MovieWithMovieGenre>?

    @Query("select * from movie where movieId=:id")
    abstract fun getMovieByIdFlow(id: Int): Flow<MovieWithMovieGenre?>

    @Query("select * from movie where movieId=:id")
    abstract suspend fun getMovieById(id: Int): MovieWithMovieGenre?

    @Transaction
    @Query("select * from favoritemovie where isFavorite=1")
    abstract fun getLikedMovies(): Flow<List<LikedMovie>?>
}