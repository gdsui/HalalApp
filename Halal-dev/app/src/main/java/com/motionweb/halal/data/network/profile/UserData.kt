package com.motionweb.halal.data.network.profile

import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody

data class UserData(
    val image: MultipartBody.Part,
    val username: String,
    val email: String,
    val phone: String,
    val gender: String,
    @SerializedName("favorite_companies")
    val favoriteCompanies: List<Int>,
    val password: String
)