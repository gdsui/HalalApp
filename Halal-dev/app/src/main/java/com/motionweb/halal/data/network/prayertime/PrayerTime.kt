package com.motionweb.halal.data.network.prayertime

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class PrayerTime(
    val id: Int,
    val date: String,
    @SerializedName("first_time")
    val firstTime: String,
    @SerializedName("second_time")
    val secondTime: String,
    @SerializedName("third_time")
    val thirdTime: String,
    @SerializedName("fourth_time")
    val fourthTime: String,
    @SerializedName("fifth_time")
    val fifthTime: String,
    @SerializedName("sixth_time")
    val sixthTime: String
) : Parcelable