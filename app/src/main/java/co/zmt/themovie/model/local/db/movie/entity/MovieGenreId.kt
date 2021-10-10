package co.zmt.themovie.model.local.db.movie.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [ForeignKey(
        entity = Movie::class,
        parentColumns = ["id"],
        childColumns = ["movieId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class MovieGenreId(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val genreId: Int,
    val movieId: Int,
    val genreName: String
)