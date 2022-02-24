package com.motionweb.halal.data.network.certificates

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Certificate(
    val id: Int,
    val logo: String,
    val title: String,
    val description: String,
    val certificates: CertificateImages
) : Parcelable

@Parcelize
data class CertificateImages(
    val id: Int,
    @SerializedName("image1")
    val imageOne: String,
    @SerializedName("image2")
    val imageSecond: String
) : Parcelable