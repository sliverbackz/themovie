package co.zmt.themovie.model.local.db.movie.dao

import androidx.room.Dao
import androidx.room.Query
import co.zmt.themovie.model.local.db.movie.entity.MovieGenre
import kotlinx.coroutines.flow.Flow

@Dao
abstract class MovieGenreDao : BaseDao<MovieGenre> {
    @Query("select * from moviegenre")
    abstract suspend fun getAllMovieGenre(): List<MovieGenre>

    @Query("select * from moviegenre")
    abstract fun getAllMovieGenreFlow(): Flow<List<MovieGenre>>

    @Query("select name from moviegenre where id =:id")
    abstract fun getGenreName(id: Int): String
}