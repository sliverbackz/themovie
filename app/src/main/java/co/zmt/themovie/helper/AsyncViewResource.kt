package co.zmt.themovie.helper

sealed class AsyncViewResource<out T> {
    open operator fun invoke(): T? = null
    class Loading<out T> : AsyncViewResource<T>()
    data class Success<out T>(val value: T) : AsyncViewResource<T>()
    data class Error<out T>(val exception: Throwable, val errorMessage: String) :
        AsyncViewResource<T>()

    data class NoMoreContent<out T>(val message: String) : AsyncViewResource<T>()
}