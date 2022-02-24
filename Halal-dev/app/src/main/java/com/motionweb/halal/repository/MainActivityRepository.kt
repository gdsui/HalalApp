package com.motionweb.halal.repository

import com.motionweb.halal.data.network.prayertime.PrayerTime
import com.motionweb.halal.data.network.prayertime.PrayerTimeApi
import com.motionweb.halal.data.storage.sharedPref.AppPreferences
import javax.inject.Inject

interface MainActivityRepository {
    val prefs: AppPreferences

    suspend fun fetchAllDate(): List<PrayerTime>
    suspend fun fetchCurrentDate(date: String): PrayerTime
}

class MainActivityRepositoryImpl @Inject constructor(
    private val api: PrayerTimeApi,
    private val appPrefs: AppPreferences
) : MainActivityRepository {

    override val prefs: AppPreferences
        get() = appPrefs

    override suspend fun fetchAllDate(): List<PrayerTime> =
        api.fetchAllTime()

    override suspend fun fetchCurrentDate(date: String): PrayerTime =
        api.fetchDate(date)

}