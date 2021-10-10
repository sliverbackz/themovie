package co.zmt.themovie.repository

import androidx.lifecycle.liveData
import co.zmt.themovie.model.local.datasource.MovieLocalDataSource
import co.zmt.themovie.model.local.db.movie.entity.FavoriteMovie
import co.zmt.themovie.model.local.db.movie.entity.Movie
import co.zmt.themovie.model.local.db.movie.entity.MovieGenre
import co.zmt.themovie.model.local.db.movie.entity.MovieGenreId
import co.zmt.themovie.model.remote.Resource
import co.zmt.themovie.model.remote.datasource.MovieNetworkDataSource
import co.zmt.themovie.repository.entitymapper.MovieEntityMapper
import co.zmt.themovie.repository.entitymapper.MovieGenreEntityMapper
import timber.log.Timber
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val movieNetworkDataSource: MovieNetworkDataSource,
    private val movieLocalDataSource: MovieLocalDataSource,
    private val movieEntityMapper: MovieEntityMapper,
    private val movieGenreEntityMapper: MovieGenreEntityMapper
) {

    suspend fun getGenreList(): AsyncResource<List<MovieGenre>?> {
        when (val response = movieNetworkDataSource.getMovieGenreList()) {
            is Resource.Success -> {
                val mappedData = response.data.genres?.map {
                    movieGenreEntityMapper.map(it)
                }
                mappedData?.let {
                    movieLocalDataSource.bulkMovieGenreInsert(it)
                }
                return AsyncResource.Success(mappedData)
            }
            is Resource.Error -> {
                return AsyncResource.Error(response.throwable, response.message)
            }
            is Resource.Loading -> {
                return AsyncResource.Loading()
            }
            else -> return AsyncResource.Loading()
        }
    }

    fun getLiveData() = liveData {
        emitSource(movieLocalDataSource.getMovieGenreLiveData())
    }

    fun getGenreListFlow() = movieLocalDataSource.getMovieGenreFlow()

    fun getMovieDetailFlow(id: Int) = movieLocalDataSource.getMovieDetailFlow(id)

    suspend fun getMovieDetail(id: Int) = movieLocalDataSource.getMovieDetail(id)

    suspend fun getFavoriteMovie(movieId: Int) = movieLocalDataSource.getFavoriteMovie(movieId)

    suspend fun insertFavoriteMovie(favMovie: FavoriteMovie) =
        movieLocalDataSource.insertFavoriteMovie(favMovie)

    suspend fun updateFavoriteMovie(favMovie: FavoriteMovie) =
        movieLocalDataSource.updateFavoriteMovie(favMovie)

    suspend fun getUpcomingMovies(): AsyncResource<List<Movie>?> {
        when (val response = movieNetworkDataSource.getUpcomingMovies()) {
            is Resource.Success -> {
                val mappedMovieData = response.data.results
                    ?.map(movieEntityMapper::map)
                    ?.apply { movieLocalDataSource.bulkMovieInsert(this) }

                response.data.results?.forEach { data ->
                    data.genreIds?.map {
                        Timber.i("${data.id} =>$it")
                        MovieGenreId(
                            movieId = data.id,
                            genreId = it,
                            genreName = movieLocalDataSource.getGenreNameById(it)
                        )
                    }?.apply { movieLocalDataSource.bulkMovieGenreIdInsert(this) }

                }
                return AsyncResource.Success(mappedMovieData)
            }
            is Resource.Error -> {
                return AsyncResource.Error(response.throwable, response.message)
            }
            is Resource.Loading -> {
                return AsyncResource.Loading()
            }
            else -> return AsyncResource.Loading()
        }
    }

    fun getUpcomingMoviesFlow() = movieLocalDataSource.getMovieFlow()

    fun getPopularMoviesFlow() = movieLocalDataSource.getMovieFlow()

    fun getLikedMovieFlow() = movieLocalDataSource.getLikedMovieFlow()

    suspend fun getPopularMovies(): AsyncResource<List<Movie>?> {
        when (val response = movieNetworkDataSource.getPopularMovies()) {
            is Resource.Success -> {
                val mappedMovieData = response.data.results
                    ?.map(movieEntityMapper::map)
                    ?.apply { movieLocalDataSource.bulkMovieInsert(this) }

                response.data.results?.forEach { data ->
                    data.genreIds?.map {
                        Timber.i("${data.id} =>$it")
                        MovieGenreId(
                            movieId = data.id,
                            genreId = it,
                            genreName = movieLocalDataSource.getGenreNameById(it)
                        )
                    }?.apply { movieLocalDataSource.bulkMovieGenreIdInsert(this) }

                }
                return AsyncResource.Success(mappedMovieData)
            }
            is Resource.Error -> {
                return AsyncResource.Error(response.throwable, response.message)
            }
            is Resource.Loading -> {
                return AsyncResource.Loading()
            }
            else -> return AsyncResource.Loading()
        }
    }

}