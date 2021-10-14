package co.zmt.themovie.helper

sealed class UiState<out T> {
    open operator fun invoke(): T? = null
    class Loading<out T> : UiState<T>()
    data class Success<out T>(val value: T) : UiState<T>()
    data class Error<out T>(val exception: Throwable, val errorMessage: String) :
        UiState<T>()
}