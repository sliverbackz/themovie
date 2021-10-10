package co.zmt.themovie.model.remote.interceptors

interface AuthTokenStore {

    fun storeToken(token: Token)

    fun retrieveToken(): Token

}
