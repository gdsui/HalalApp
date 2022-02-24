package com.motionweb.halal.repository

import com.google.gson.Gson
import com.motionweb.halal.data.storage.sharedPref.AppPreferences
import com.motionweb.halal.utils.prayerTime.PrayerTimeWallpaper
import javax.inject.Inject

interface PrayerTimeRepository {
    fun getPrayerTime(): PrayerTimeWallpaper?
}

class PrayerTimeRepositoryImpl @Inject constructor(
    private val appPrefs: AppPreferences
) : PrayerTimeRepository {

    override fun getPrayerTime(): PrayerTimeWallpaper? =
        Gson().fromJson(appPrefs.prayerModel, PrayerTimeWallpaper::class.java)

}