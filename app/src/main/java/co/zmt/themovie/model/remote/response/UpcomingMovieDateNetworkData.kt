package co.zmt.themovie.model.remote.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UpcomingMovieDateNetworkData(
    @Json(name = "maximum") val maximum: String,
    @Json(name = "minimum") val minimum: String
)