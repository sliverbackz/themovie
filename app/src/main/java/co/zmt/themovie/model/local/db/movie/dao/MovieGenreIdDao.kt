package co.zmt.themovie.model.local.db.movie.dao

import androidx.room.Dao
import androidx.room.Query
import co.zmt.themovie.model.local.db.movie.entity.MovieGenre
import co.zmt.themovie.model.local.db.movie.entity.MovieGenreId
import kotlinx.coroutines.flow.Flow

@Dao
abstract class MovieGenreIdDao : BaseDao<MovieGenreId> {
    @Query("select genreName from moviegenreid where movieId =:movieId  ")
    abstract fun getMovieGenreNameListByMovieId(movieId: Int): Flow<List<String>>
}