package com.motionweb.halal.repository

import com.motionweb.halal.data.network.favorite.FavoriteApi
import com.motionweb.halal.data.network.favorite.FavoriteRequestModel
import com.motionweb.halal.data.network.favorite.FavoriteResponseModel
import com.motionweb.halal.data.network.profile.AuthApi
import com.motionweb.halal.data.network.profile.ProfileApi
import com.motionweb.halal.data.network.profile.UserModel
import com.motionweb.halal.data.storage.db.FavoriteDao
import com.motionweb.halal.data.storage.db.FavoriteEntity
import com.motionweb.halal.data.storage.sharedPref.AppPreferences
import com.motionweb.halal.utils.Gender
import okhttp3.MultipartBody
import javax.inject.Inject

interface ProfileRepository {

    val isAuth: Boolean
    val username: String
    val phone: String
    val email: String
    val gender: String
    val avatarPath: String

    suspend fun register(
        avatar: MultipartBody.Part?,
        username: String,
        email: String,
        phone: String,
        gender: String,
        password: String,
        favoriteCompanies: List<Int>?
    ): UserModel

    suspend fun login(email: String, password: String): Boolean

    suspend fun setIsAuthorized(isAuth: Boolean)

    fun setUsername(name: String)
    fun setUserEmail(email: String)
    fun setPassword(password: String)
    fun setGender(gender: Gender)
    fun setAvatarPath(path: String)
    fun setPhone(phone: String)

    suspend fun fetchFavorites(): List<FavoriteEntity>
    suspend fun setFavoriteCompanies(favoriteCompanies: List<Int>): FavoriteResponseModel
    suspend fun fetchProfile(): UserModel?

}

class ProfileRepositoryImpl @Inject constructor(
    private val api: AuthApi,
    private val appPrefs: AppPreferences,
    private val dao: FavoriteDao,
    private val favoriteApi: FavoriteApi,
    private val profileApi: ProfileApi
) : ProfileRepository {

    override val isAuth: Boolean get() = appPrefs.isAuthorized

    override val username: String get() = appPrefs.username

    override val email: String get() = appPrefs.email

    override val phone: String get() = appPrefs.userPhone

    override val gender: String get() = appPrefs.gender

    override val avatarPath: String get() = appPrefs.avatarPath

    override suspend fun register(
        avatar: MultipartBody.Part?,
        username: String,
        email: String,
        phone: String,
        gender: String,
        password: String,
        favoriteCompanies: List<Int>?
    ): UserModel =
        api.register(avatar, username, email, phone, gender, password, favoriteCompanies)

    override suspend fun login(email: String, password: String): Boolean {
        val response = api.login(email, password)
        appPrefs.accessToken = response.access
        appPrefs.refreshToken = response.refresh
        return response.access.isNotEmpty() && response.refresh.isNotEmpty()
    }

    override suspend fun setIsAuthorized(isAuth: Boolean) {
        appPrefs.isAuthorized = isAuth
        if (!isAuth) {
            appPrefs.clearUserData()
            dao.clearAll()
        }
    }

    override fun setUsername(name: String) {
        appPrefs.username = name.replace("\"", "")
    }

    override fun setUserEmail(email: String) {
        appPrefs.email = email.replace("\"", "")
    }

    override fun setPassword(password: String) {
        appPrefs.password = password.replace("\"", "")
    }

    override fun setGender(gender: Gender) {
        appPrefs.gender = gender.gender
    }

    override fun setAvatarPath(path: String) {
        appPrefs.avatarPath = path
    }

    override fun setPhone(phone: String) {
        appPrefs.userPhone = phone.replace("\"", "")
    }

    override suspend fun fetchFavorites(): List<FavoriteEntity> = dao.fetchAllFavorites()

    override suspend fun setFavoriteCompanies(favoriteCompanies: List<Int>): FavoriteResponseModel {
        return favoriteApi.addFavorite(FavoriteRequestModel(favoriteCompanies))
    }

    override suspend fun fetchProfile(): UserModel? = profileApi.fetchProfile()

}