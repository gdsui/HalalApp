package com.motionweb.halal.data.network.catalog.models

import com.google.gson.annotations.SerializedName

data class Company(
    val id: Int,
    val name: String,
    val logo: String?,
    @SerializedName("category")
    val category: Int,
    var isFavorite: Boolean = false
)

data class CompanyDetail(
    val id: Int,
    val name: String,
    val logo: String?,
    @SerializedName("category")
    val category: Category
)