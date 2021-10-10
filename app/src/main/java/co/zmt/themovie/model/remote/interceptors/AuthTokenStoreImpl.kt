package co.zmt.themovie.model.remote.interceptors

import android.content.Context
import androidx.core.content.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber
import javax.inject.Inject

class AuthTokenStoreImpl @Inject constructor(@ApplicationContext context: Context) :
    AuthTokenStore {
    private val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREF_KEY_TOKEN = "token"
        private const val PREF_NAME = "pref.authtokenstore.name"
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