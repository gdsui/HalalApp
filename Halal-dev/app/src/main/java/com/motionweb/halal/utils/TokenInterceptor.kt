package com.motionweb.halal.utils

import android.util.Log
import com.motionweb.halal.data.network.auth.Token
import com.motionweb.halal.data.network.profile.AuthApi
import com.motionweb.halal.data.storage.sharedPref.AppPreferences
import kotlinx.coroutines.runBlocking
import okhttp3.*
import javax.inject.Inject

class TokenInterceptor @Inject constructor(private val appPref: AppPreferences, private val api: AuthApi) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        val refreshToken = appPref.refreshToken
        if (refreshToken.isNullOrEmpty().not()) {
            try {
                val tokenResponse = runBlocking {
                    api.refreshToken(Token(refresh = appPref.refreshToken))
                }
                appPref.accessToken = tokenResponse.access
                return response.request.newBuilder()
                    .header("Authorization", "Bearer ${tokenResponse.access}")
                    .build()
            } catch (e: Exception) {
                Log.d("TokenAuth Error:", "$e")
            }
        }
        return null
    }

}