package co.zmt.themovie.model.remote.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieGenreResponse(
    @Json(name = "genres") val genres: List<MovieGenreNetworkData>?
)


