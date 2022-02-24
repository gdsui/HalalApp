package com.motionweb.halal.data.network.catalog.models

import com.google.gson.annotations.SerializedName

data class Product(
    val id: Int,
    @SerializedName("title_ru")
    val titleRu: String,
    @SerializedName("title_ky")
    val titleKg: String,
    val company: Int,
    val type: String,
    val photo: String?,
    @SerializedName("qr_image")
    val qrImage: String?,
    @SerializedName("qr_code")
    val qrCode: String?,
)