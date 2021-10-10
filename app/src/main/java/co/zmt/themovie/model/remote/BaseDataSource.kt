package co.zmt.themovie.model.remote

import retrofit2.Response
import timber.log.Timber

abstract class BaseDataSource {

    companion object {
        private const val ERROR_CODE_400 = 400
        private const val ERROR_CODE_401 = 401
        private const val ERROR_CODE_422 = 422
        private const val ERROR_CODE_403 = 403
        private const val ERROR_CODE_404 = 404
        private const val ERROR_CODE_500 = 500
    }

    protected suspend fun <T> getResult(call: suspend () -> Response<T>): Resource<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return Resource.Success(body)
            }
            return Resource.Error(
                " ${response.code()} ${response.message()}",
                Throwable(response.code().toString()),
                null
            )
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(message: String): Resource<T> {
        Timber.d(message)
        return Resource.Error(
            "Network call has failed for a following reason: $message",
            Throwable(message),
            null
        )
    }

}
