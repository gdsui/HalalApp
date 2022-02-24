package com.motionweb.halal.data.network.barcode_scanner

import com.google.gson.annotations.SerializedName

data class Barcode(
    val id: Int,
    @SerializedName("title_ru")
    val titleRU: String,
    @SerializedName("title_ky")
    val titleKG: String,
    val photo: String,
    @SerializedName("company")
    val companyId: Int,
    @SerializedName("qr_image")
    val qrImage: String,
    @SerializedName("qr_code")
    val qrCode: String,
    val type: String
)