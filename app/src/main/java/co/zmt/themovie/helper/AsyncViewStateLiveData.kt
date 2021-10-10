package co.zmt.themovie.helper

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import timber.log.Timber

/**
 * A lifecycle-aware observable that sends Error state only once but revert back to normal LiveData
 * for Loading and Success. This is to prevent Error from being shown again to user on config changes
 */
class AsyncViewStateLiveData<T> : LiveData<AsyncViewResource<T>>() {

    override fun observe(owner: LifecycleOwner, observer: Observer<in AsyncViewResource<T>>) {
        if (hasActiveObservers()) {
            Timber.w(
                "Multiple observers registered but only one will be notified of changes."
            )
        }
        super.observe(owner, observer)
    }

    fun postLoading() {
        this.postValue(AsyncViewResource.Loading())
    }

    fun postSuccess(data: T) {
        this.postValue(AsyncViewResource.Success(data))
    }

    fun postError(exception: Throwable, error: String = "") {
        this.postValue(AsyncViewResource.Error(exception, error))
    }

}