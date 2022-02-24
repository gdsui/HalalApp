package com.motionweb.halal.data.network.favorite

import com.google.gson.annotations.SerializedName

data class FavoriteRequestModel(
    @SerializedName("favorite_companies")
    val favoriteCompanies: List<Int>
)

data class FavoriteResponseModel(
    val id: Int,
    val username: String,
    val email: String,
    val avatar: String,
    val phone: String,
    val gender: String,
    @SerializedName("creation_date")
    val creationDate: String,
    @SerializedName("update_data")
    val updateDate: String,
    @SerializedName("favorite_companies")
    val favoriteCompanies: List<Int>
)