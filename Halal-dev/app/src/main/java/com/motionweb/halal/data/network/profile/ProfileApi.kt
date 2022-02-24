package com.motionweb.halal.data.network.profile

import retrofit2.http.POST

interface ProfileApi {

    @POST("auth/profile/")
    suspend fun fetchProfile(): UserModel?

}