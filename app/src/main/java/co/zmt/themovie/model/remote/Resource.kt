package co.zmt.themovie.model.remote

sealed class Resource<out T> {

    data class Success<out T>(val data: T) : Resource<T>()

    data class Error<out T>(val message: String, val throwable: Throwable, val data: T?) :
        Resource<T>()

    data class Loading<out T>(val isLoading: Boolean = true) : Resource<T>()

    data class Start<out T>(val checkLocalData: String = "ok") : Resource<T>()

}
