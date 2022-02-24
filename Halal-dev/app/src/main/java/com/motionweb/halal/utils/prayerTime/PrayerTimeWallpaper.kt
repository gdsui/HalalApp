package com.motionweb.halal.utils.prayerTime

import android.os.Parcelable
import com.motionweb.halal.data.network.prayertime.PrayerTime
import kotlinx.parcelize.Parcelize

@Parcelize
data class PrayerTimeWallpaper(
    val prayerTime: PrayerTime,
    val index: Int
) : Parcelable