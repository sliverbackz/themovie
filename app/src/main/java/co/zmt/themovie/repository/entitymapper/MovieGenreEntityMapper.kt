package co.zmt.themovie.repository.entitymapper

import co.zmt.themovie.model.local.db.movie.entity.MovieGenre
import co.zmt.themovie.model.remote.response.MovieGenreNetworkData
import javax.inject.Inject

class MovieGenreEntityMapper @Inject constructor() :
    UnidirectionalMap<MovieGenreNetworkData, MovieGenre> {
    override fun map(item: MovieGenreNetworkData): MovieGenre {
        return MovieGenre(
            id = item.id,
            name = item.name
        )
    }
}