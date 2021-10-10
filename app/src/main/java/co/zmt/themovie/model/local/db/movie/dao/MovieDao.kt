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
    abstract fun getAllMovie(): Flow<List<MovieWithMovieGenre>?>

    @Query("select * from movie where id=:id")
    abstract fun getMovieByIdFlow(id: Int): Flow<MovieWithMovieGenre?>

    @Query("select * from movie where id=:id")
    abstract suspend fun getMovieById(id: Int): MovieWithMovieGenre?

    @Transaction
    @Query("select * from movie")
    abstract fun getLikedMovies(): Flow<List<LikedMovie>?>
}