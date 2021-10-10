package co.zmt.themovie.model.remote

import co.zmt.themovie.model.remote.OldResource.Status.ERROR
import co.zmt.themovie.model.remote.OldResource.Status.LOADING
import co.zmt.themovie.model.remote.OldResource.Status.SUCCESS

data class OldResource<out T>(val status: Status, val data: T?, val message: String?) {

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    companion object {
        fun <T> success(data: T): OldResource<T> {
            return OldResource(SUCCESS, data, null)
        }

        fun <T> error(message: String, data: T? = null): OldResource<T> {
            return OldResource(ERROR, data, message)
        }

        fun <T> loading(data: T? = null): OldResource<T> {
            return OldResource(LOADING, data, null)
        }
    }
}
