package co.zmt.themovie.model.local.db.movie.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [ForeignKey(
        entity = Movie::class,
        parentColumns = ["movieId"],
        childColumns = ["movieId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class FavoriteMovie(
    @PrimaryKey
    val movieId: Int,
    val isFavorite: Boolean
)