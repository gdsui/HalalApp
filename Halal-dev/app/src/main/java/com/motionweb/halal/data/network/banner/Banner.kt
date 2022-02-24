package com.motionweb.halal.data.network.banner

import com.google.gson.annotations.SerializedName

data class Banner(
    val id: Int,
    val banner: String,
    @SerializedName("creation_date")
    val creationDate: String,
    @SerializedName("update_date")
    val updatedDate: String
)