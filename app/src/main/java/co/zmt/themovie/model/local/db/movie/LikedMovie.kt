package co.zmt.themovie.model.local.db.movie

import androidx.room.Embedded
import androidx.room.Relation
import co.zmt.themovie.model.local.db.movie.entity.FavoriteMovie
import co.zmt.themovie.model.local.db.movie.entity.Movie

data class LikedMovie(
    @Embedded
    val movie: Movie,
    @Relation(
        parentColumn = "id",
        entityColumn = "movieId"
    )
    val favoriteMovie: FavoriteMovie?
)