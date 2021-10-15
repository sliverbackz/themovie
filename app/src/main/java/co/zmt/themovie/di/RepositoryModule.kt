package co.zmt.themovie.di

import co.zmt.themovie.model.local.datasource.MovieLocalDataSource
import co.zmt.themovie.model.remote.datasource.MovieNetworkDataSource
import co.zmt.themovie.repository.MovieRepository
import co.zmt.themovie.repository.entitymapper.MovieEntityMapper
import co.zmt.themovie.repository.entitymapper.MovieGenreEntityMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {
    @Provides
    @ViewModelScoped
    fun provideMovieRepository(
        movieNetworkDataSource: MovieNetworkDataSource,
        movieLocalDataSource: MovieLocalDataSource,
        movieEntityMapper: MovieEntityMapper,
        movieGenreEntityMapper: MovieGenreEntityMapper
    ): MovieRepository {
        return MovieRepository(
            movieNetworkDataSource,
            movieLocalDataSource,
            movieEntityMapper,
            movieGenreEntityMapper
        )
    }
}