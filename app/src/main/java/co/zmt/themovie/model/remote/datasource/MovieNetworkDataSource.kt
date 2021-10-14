package co.zmt.themovie.model.remote.datasource

import co.zmt.themovie.model.remote.BaseDataSource
import co.zmt.themovie.model.remote.Resource
import co.zmt.themovie.model.remote.response.MovieGenreResponse
import co.zmt.themovie.model.remote.response.PopularMovieResponse
import co.zmt.themovie.model.remote.response.UpcomingMovieResponse
import co.zmt.themovie.model.remote.service.MovieService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieNetworkDataSource @Inject constructor(
    private val movieService: MovieService
) : BaseDataSource() {

    suspend fun getUpcomingMoviesFlow(): Flow<Resource<UpcomingMovieResponse>> {
        return flow {
            emit(Resource.Loading(isLoading = true))
            emit(getResult { movieService.getUpcomingMovies(1) })
        }
    }

    suspend fun getPopularMovies(): Resource<PopularMovieResponse> {
        return getResult {
            movieService.getPopularMovies(1)
        }

    }

    suspend fun getMovieGenreList(): Resource<MovieGenreResponse> {
        return getResult {
            movieService.getMovieGenreList()
        }
    }
}