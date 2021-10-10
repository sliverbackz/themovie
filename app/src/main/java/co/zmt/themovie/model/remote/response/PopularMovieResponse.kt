package co.zmt.themovie.model.remote.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PopularMovieResponse(
    @Json(name = "page") val page: Int?,
    @Json(name = "results") val results: List<MovieNetworkData>?,
    @Json(name = "total_pages") val totalPage: Int?,
    @Json(name = "total_results") val totalResult: Int?
)