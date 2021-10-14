package co.zmt.themovie.repository

import androidx.lifecycle.liveData
import co.zmt.themovie.helper.State
import co.zmt.themovie.model.local.datasource.MovieLocalDataSource
import co.zmt.themovie.model.local.db.movie.MovieWithMovieGenre
import co.zmt.themovie.model.local.db.movie.entity.FavoriteMovie
import co.zmt.themovie.model.local.db.movie.entity.Movie
import co.zmt.themovie.model.local.db.movie.entity.MovieGenre
import co.zmt.themovie.model.local.db.movie.entity.MovieGenreId
import co.zmt.themovie.model.remote.Resource
import co.zmt.themovie.model.remote.datasource.MovieNetworkDataSource
import co.zmt.themovie.repository.entitymapper.MovieEntityMapper
import co.zmt.themovie.repository.entitymapper.MovieGenreEntityMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
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

    fun simple() = liveData {
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

    suspend fun fetchUpcomingMovie(): Flow<State<List<MovieWithMovieGenre>?>> {
        return movieNetworkDataSource.getUpcomingMoviesFlow().map {
            when (it) {
                is Resource.Start -> {
                    //produce local data
                    movieLocalDataSource.getMovies()
                    State.Start(movieLocalDataSource.getMovies())
                }
                is Resource.Success -> {
                    it.data.results
                        ?.map(movieEntityMapper::map)
                        ?.apply { movieLocalDataSource.bulkMovieInsert(this) }
                    it.data.results?.forEach { data ->
                        data.genreIds?.map { genre ->
                            MovieGenreId(
                                movieId = data.id,
                                genreId = genre,
                                genreName = movieLocalDataSource.getGenreNameById(genre)
                            )
                        }?.apply { movieLocalDataSource.bulkMovieGenreIdInsert(this) }

                    }
                    //produce local or network data
                    State.Success(movieLocalDataSource.getMovies())
                }
                is Resource.Error -> {
                    State.Error(it.throwable, it.message)
                }
                is Resource.Loading -> {
                    State.Loading()
                }
            }
        }
    }

    fun getUpcomingMoviesFlow() = movieLocalDataSource.getMovieFlow()

    fun getUpcomingMovies() = movieLocalDataSource.getMovies()

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