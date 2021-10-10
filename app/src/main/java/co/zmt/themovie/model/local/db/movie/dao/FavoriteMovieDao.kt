package co.zmt.themovie.model.local.db.movie.dao

import androidx.room.Dao
import androidx.room.Query
import co.zmt.themovie.model.local.db.movie.entity.FavoriteMovie

@Dao
abstract class FavoriteMovieDao : BaseDao<FavoriteMovie> {
    @Query("select * from favoritemovie where movieId=:movieId")
    abstract suspend fun getFavoriteMovieById(movieId: Int): FavoriteMovie?

}