package co.zmt.themovie.repository.entitymapper

import co.zmt.themovie.model.local.db.movie.entity.Movie
import co.zmt.themovie.model.remote.response.MovieNetworkData
import javax.inject.Inject

class MovieEntityMapper @Inject constructor() : UnidirectionalMap<MovieNetworkData, Movie> {
    override fun map(item: MovieNetworkData): Movie {
        return Movie(
            id = item.id,
            adult = item.adult,
            backdropPath = item.backdropPath,
            originalLanguage = item.originalLanguage,
            originalTitle = item.originalTitle,
            overview = item.overview,
            popularity = item.popularity,
            posterPath = item.posterPath,
            releaseDate = item.releaseDate,
            title = item.title,
            video = item.video,
            voteAverage = item.voteAverage,
            voteCount = item.voteCount
        )
    }

}