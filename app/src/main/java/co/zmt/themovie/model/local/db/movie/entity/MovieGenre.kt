package co.zmt.themovie.model.local.db.movie.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieGenre(
    @PrimaryKey
    val id: Int,
    val name: String
)