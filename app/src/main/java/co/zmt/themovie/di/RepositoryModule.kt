package co.zmt.themovie.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

//@Module
//@InstallIn(ViewModelComponent::class)
//object RepositoryModule {
//    @Provides
//    @ViewModelScoped
//    fun provideDiscoverRepository(
//        discoverService: TheDiscoverService,
//        movieDao: MovieDao,
//        tvDao: TvDao
//    ): DiscoverRepository {
//        return DiscoverRepository(discoverService, movieDao, tvDao)
//    }
//}