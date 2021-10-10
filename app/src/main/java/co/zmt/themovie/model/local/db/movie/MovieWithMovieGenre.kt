package co.zmt.themovie.model.local.db.movie

import androidx.room.Embedded
import androidx.room.Relation
import co.zmt.themovie.model.local.db.movie.entity.FavoriteMovie
import co.zmt.themovie.model.local.db.movie.entity.Movie
import co.zmt.themovie.model.local.db.movie.entity.MovieGenreId

data class MovieWithMovieGenre(
    @Embedded
    val movie: Movie,
    @Relation(
        parentColumn = "id",
        entityColumn = "movieId"
    )
    val genreIds: List<MovieGenreId>,

    @Relation(
        parentColumn = "id",
        entityColumn = "movieId"
    )
    val favoriteMovie: FavoriteMovie?
)