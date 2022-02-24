package com.motionweb.halal.data.network.catalog.models

import com.google.gson.annotations.SerializedName

data class Category(
    val id: Int,
    @SerializedName("name_ru")
    val nameRu: String,
    @SerializedName("name_ky")
    val nameKg: String,
    val logo: String?,
)
