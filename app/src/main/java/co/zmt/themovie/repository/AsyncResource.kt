package co.zmt.themovie.repository

sealed class AsyncResource<out T> {
    open operator fun invoke(): T? = null
    class Loading<out T> : AsyncResource<T>()
    data class Success<out T>(val value: T) : AsyncResource<T>()
    data class Error<out T>(val exception: Throwable, val errorMessage: String) :
        AsyncResource<T>()

//    data class NoMoreContent<out T>(val message: String) : AsyncResource<T>()
}