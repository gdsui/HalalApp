package com.motionweb.halal.data.network.profile

import com.motionweb.halal.data.network.auth.Token
import okhttp3.MultipartBody
import retrofit2.http.*


interface AuthApi {

    @Multipart
    @POST("auth/register/")
    suspend fun register(
        @Part avatar: MultipartBody.Part?,
        @Part("username") username: String,
        @Part("email") email: String,
        @Part("phone") phone: String,
        @Part("gender") gender: String,
        @Part("password") password: String,
        @Part("favorite_companies") favoriteCompanies: List<Int>?
    ): UserModel

    @Multipart
    @POST("auth/login/")
    suspend fun login(
        @Part("email") email: String,
        @Part("password") password: String
    ): Token


    @POST("auth/refresh/")
    suspend fun refreshToken(@Body token: Token): Token

}