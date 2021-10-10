package co.zmt.themovie.di

import co.zmt.themovie.model.local.db.AppDatabase
import co.zmt.themovie.model.local.db.movie.dao.FavoriteMovieDao
import co.zmt.themovie.model.local.db.movie.dao.MovieDao
import co.zmt.themovie.model.local.db.movie.dao.MovieGenreDao
import co.zmt.themovie.model.local.db.movie.dao.MovieGenreIdDao
import co.zmt.themovie.model.remote.service.MovieService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    //Room Dao to provide interfaces for getting local data
    @Provides
    fun provideMovieDao(database: AppDatabase): MovieDao {
        return database.movieDao()
    }

    @Provides
    fun provideMovieGenreDao(database: AppDatabase): MovieGenreDao {
        return database.movieGenreDao()
    }

    @Provides
    fun provideMovieGenreIdDao(database: AppDatabase): MovieGenreIdDao {
        return database.movieGenreIdDao()
    }

    @Provides
    fun provideFavoriteMovieDao(database: AppDatabase): FavoriteMovieDao {
        return database.favoriteMovieDao()
    }

    //Retrofit service for creating interfaces to perform API calls

    @Provides
    fun provideMovieService(retrofit: Retrofit): MovieService {
        return retrofit.create(MovieService::class.java)
    }
}