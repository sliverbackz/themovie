package co.zmt.themovie.repository

import co.zmt.themovie.helper.State
import co.zmt.themovie.model.local.datasource.MovieLocalDataSource
import co.zmt.themovie.model.local.db.movie.MovieWithMovieGenre
import co.zmt.themovie.model.local.db.movie.entity.FavoriteMovie
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
    suspend fun fetchGenreList(): Flow<State<List<MovieGenre>?>> {
        return movieNetworkDataSource.getMovieGenreList().map {
            when (it) {
                is Resource.Start -> {
                    //fetch local data
                    State.Start(movieLocalDataSource.getAllMovieGenre())
                }
                is Resource.Success -> {
                    val mappedData = it.data.genres?.map { data ->
                        movieGenreEntityMapper.map(data)
                    }
                    if (mappedData != null) {
                        movieLocalDataSource.bulkMovieGenreInsert(mappedData)
                    }
                    //fetch local data or network
                    State.Success(movieLocalDataSource.getAllMovieGenre())
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

    suspend fun fetchUpcomingMovie(): Flow<State<List<MovieWithMovieGenre>?>> {
        return movieNetworkDataSource.getUpcomingMoviesFlow().map {
            when (it) {
                is Resource.Start -> {
                    //produce local data
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

    suspend fun fetchPopularMovies(): Flow<State<List<MovieWithMovieGenre>?>> {
        return movieNetworkDataSource.getPopularMovies().map {
            when (it) {
                is Resource.Start -> {
                    State.Start(movieLocalDataSource.getMovies())
                }
                is Resource.Success -> {
                    it.data.results
                        ?.map(movieEntityMapper::map)
                        ?.apply { movieLocalDataSource.bulkMovieInsert(this) }

                    it.data.results?.forEach { data ->
                        data.genreIds?.map { genre ->
                            Timber.i("${data.id} =>$genre")
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

    fun getGenreListFlow() = movieLocalDataSource.getMovieGenreFlow()

    fun getMovieGenreNameListByMovieId(id: Int) = movieLocalDataSource.getGenreNameListByMovieId(id)

    fun getMovieDetailFlow(id: Int) = movieLocalDataSource.getMovieDetailFlow(id)

    suspend fun getMovieDetail(id: Int) = movieLocalDataSource.getMovieDetail(id)

    suspend fun getFavoriteMovie(movieId: Int) = movieLocalDataSource.getFavoriteMovie(movieId)

    suspend fun insertFavoriteMovie(favMovie: FavoriteMovie) = movieLocalDataSource.insertFavoriteMovie(favMovie)

    fun getPopularMoviesFlow() = movieLocalDataSource.getMovieFlow()

    fun getLikedMovieFlow() = movieLocalDataSource.getLikedMovieFlow()

}