package co.zmt.themovie.helper

import timber.log.Timber

/**
 * Created by Vincent on 12/11/19
 */
fun <T> Result<out T>.doWithException(
  blockIfNull: (() -> (Unit)) = { },
  blockIfNotNull: ((Throwable) -> Unit)
) {
  val exception = this.exceptionOrNull()

  if (exception != null) {
    Timber.e(exception)
    blockIfNotNull(exception)
  } else {
    blockIfNull()
  }

}

