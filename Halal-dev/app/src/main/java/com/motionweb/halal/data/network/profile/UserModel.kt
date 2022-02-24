package com.motionweb.halal.data.network.profile

import com.google.gson.annotations.SerializedName

data class UserModel(
    val id: Int,
    val username: String,
    val email: String,
    val avatar: String,
    val phone: String,
    val gender: String,
    @SerializedName("creation_date")
    val creationDate: String?,
    @SerializedName("update_date")
    val updateDate: String?,
    @SerializedName("favorite_companies")
    val favoriteCompanies: List<Int>?
)