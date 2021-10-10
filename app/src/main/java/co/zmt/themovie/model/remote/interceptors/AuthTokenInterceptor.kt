package co.zmt.themovie.model.remote.interceptors

import co.zmt.themovie.di.NormalPref
import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber
import javax.inject.Inject

class AuthTokenInterceptor @Inject constructor(
    @NormalPref
    private val authStore: AuthTokenStore
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = authStore.retrieveToken().value ?: return chain.proceed(chain.request())
        val requestBuilder = chain.request().newBuilder()
        Timber.i(authStore.retrieveToken().value)
        requestBuilder.addHeader("Authorization", "Bearer $token")
        val newRequest = requestBuilder.build()
        return chain.proceed(newRequest)
    }
}

