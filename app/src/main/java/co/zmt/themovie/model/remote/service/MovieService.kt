package co.zmt.themovie.model.remote.service

import co.zmt.themovie.model.remote.response.MovieGenreResponse
import co.zmt.themovie.model.remote.response.PopularMovieResponse
import co.zmt.themovie.model.remote.response.UpcomingMovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {
    @GET("3/movie/upcoming")
    suspend fun getUpcomingMovies(@Query("page") page: Int)
            : Response<UpcomingMovieResponse>

    @GET("3/movie/popular")
    suspend fun getPopularMovies(@Query("page") page: Int)
            : Response<PopularMovieResponse>

    @GET("3/genre/movie/list")
    suspend fun getMovieGenreList()
            : Response<MovieGenreResponse>

}