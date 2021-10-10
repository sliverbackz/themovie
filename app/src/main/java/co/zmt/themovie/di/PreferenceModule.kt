package co.zmt.themovie.di

import co.zmt.themovie.model.remote.interceptors.AuthTokenStore
import co.zmt.themovie.model.remote.interceptors.AuthTokenStoreImpl
import co.zmt.themovie.model.remote.interceptors.AuthTokenStoreSecuredImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class PreferenceModule {
    @Binds
    @SecuredPref
    abstract fun bindAuthTokenStorage(authTokenStoreImpl: AuthTokenStoreImpl): AuthTokenStore

    @Binds
    @NormalPref
    abstract fun bindAuthTokenStoreSecured(authTokenStoreSecuredImpl: AuthTokenStoreSecuredImpl): AuthTokenStore
}