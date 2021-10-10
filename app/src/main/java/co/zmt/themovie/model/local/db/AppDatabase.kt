package co.zmt.themovie.model.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import co.zmt.themovie.model.local.db.movie.dao.FavoriteMovieDao
import co.zmt.themovie.model.local.db.movie.dao.MovieDao
import co.zmt.themovie.model.local.db.movie.dao.MovieGenreDao
import co.zmt.themovie.model.local.db.movie.dao.MovieGenreIdDao
import co.zmt.themovie.model.local.db.movie.entity.FavoriteMovie
import co.zmt.themovie.model.local.db.movie.entity.Movie
import co.zmt.themovie.model.local.db.movie.entity.MovieGenre
import co.zmt.themovie.model.local.db.movie.entity.MovieGenreId

@Database(
    entities = [Movie::class, MovieGenre::class, MovieGenreId::class, FavoriteMovie::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    abstract fun movieGenreDao(): MovieGenreDao

    abstract fun movieGenreIdDao(): MovieGenreIdDao

    abstract fun favoriteMovieDao(): FavoriteMovieDao

}
