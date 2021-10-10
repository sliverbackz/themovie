package co.zmt.themovie.model.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import co.zmt.themovie.model.remote.OldResource.Status.ERROR
import co.zmt.themovie.model.remote.OldResource.Status.SUCCESS
import kotlinx.coroutines.Dispatchers

fun <T, A> performGetOperation(
    databaseQuery: () -> LiveData<T>,
    networkCall: suspend () -> OldResource<A>,
    saveCallResult: suspend (A) -> Unit
): LiveData<OldResource<T>> =
    liveData(Dispatchers.IO) {
        emit(OldResource.loading())
        val source = databaseQuery.invoke().map { OldResource.success(it) }
        emitSource(source)

        val responseStatus = networkCall.invoke()
        if (responseStatus.status == SUCCESS) {
            saveCallResult(responseStatus.data!!)

        } else if (responseStatus.status == ERROR) {
            emit(OldResource.error(responseStatus.message!!))
            emitSource(source)
        }
    }
