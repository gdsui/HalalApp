package com.motionweb.halal.repository

import com.motionweb.halal.data.network.profile.ProfileApi
import com.motionweb.halal.data.network.profile.UserModel
import com.motionweb.halal.data.storage.sharedPref.AppPreferences
import javax.inject.Inject

interface MenuRepository {
    val username: String
    val avatarPath: String
    val isAuth: Boolean

    suspend fun fetchProfile(): UserModel?
}

class MenuRepositoryImpl @Inject constructor(
    private val appPrefs: AppPreferences,
    private val profileApi: ProfileApi
) : MenuRepository {

    override val username: String get() = appPrefs.username
    override val avatarPath: String get() = appPrefs.avatarPath
    override val isAuth: Boolean get() = appPrefs.isAuthorized

    override suspend fun fetchProfile(): UserModel? = profileApi.fetchProfile()
}