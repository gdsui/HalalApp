package com.motionweb.halal.repository

import com.motionweb.halal.data.network.profile.AuthApi
import com.motionweb.halal.data.storage.sharedPref.AppPreferences
import javax.inject.Inject

interface LoginRepository {
    suspend fun login(email: String, password: String)
}

class LoginRepositoryImpl @Inject constructor(
    private val api: AuthApi,
    private val appPrefs: AppPreferences
) : LoginRepository {

    override suspend fun login(email: String, password: String) {
        val response = api.login(email, password)
        appPrefs.accessToken = response.access
        appPrefs.refreshToken = response.refresh
    }

}