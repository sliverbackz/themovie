package co.zmt.themovie.model.local.db.movie.entity

import androidx.room.ColumnInfo
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
data class FavoriteMovie(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 1,
    @ColumnInfo(index = true)
    val movieId: Int,
    val isFavorite: Boolean
)