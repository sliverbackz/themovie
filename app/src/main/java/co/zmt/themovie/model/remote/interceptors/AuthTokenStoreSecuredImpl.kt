package co.zmt.themovie.model.remote.interceptors

import android.content.Context
import androidx.core.content.edit
import co.zmt.themovie.model.remote.securedpref.SecuredCache
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber
import javax.inject.Inject

class AuthTokenStoreSecuredImpl @Inject constructor(@ApplicationContext context: Context) :
    AuthTokenStore {
    private val sharedPreferences = SecuredCache.create(context, AUTH_STORE_FILE_NAME)

    companion object {
        private const val AUTH_STORE_FILE_NAME = "decrypt_me_XD"
        private const val PREF_KEY_TOKEN = "token"
    }

    override fun storeToken(token: Token) {
        sharedPreferences.edit {
            putString(PREF_KEY_TOKEN, token.value)
            Timber.i("Token Saved!")
        }
    }

    override fun retrieveToken(): Token {
        val token = sharedPreferences.getString(PREF_KEY_TOKEN, null)
        return Token(token)
    }
}