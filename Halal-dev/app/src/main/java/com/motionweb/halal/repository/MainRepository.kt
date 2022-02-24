package com.motionweb.halal.repository

import com.google.gson.Gson
import com.motionweb.halal.data.network.banner.Banner
import com.motionweb.halal.data.network.banner.BannerApi
import com.motionweb.halal.data.network.prayertime.PrayerTime
import com.motionweb.halal.data.network.prayertime.PrayerTimeApi
import com.motionweb.halal.data.storage.sharedPref.AppPreferences
import com.motionweb.halal.utils.prayerTime.PrayerTimeWallpaper
import javax.inject.Inject

interface MainRepository {
    suspend fun fetchAllBanners(): List<Banner>
    suspend fun fetchBanner(bannerId: Int): Banner
    suspend fun fetchCurrentDate(date: String): PrayerTime
    suspend fun fetchAllTime(): List<PrayerTime>

    fun setPrayerTime(time: String)
    fun setNearestPrayer(name: String)
    fun setPrayerTimeModel(prayerTime: PrayerTimeWallpaper)
}

class MainRepositoryImpl @Inject constructor(
    private val api: BannerApi,
    private val prayerTimeApi: PrayerTimeApi,
    private val appPrefs: AppPreferences
) : MainRepository {

    override suspend fun fetchAllBanners(): List<Banner> = api.fetchAllBanners()

    override suspend fun fetchBanner(bannerId: Int): Banner = api.fetchBanner(bannerId)

    override suspend fun fetchCurrentDate(date: String): PrayerTime =
        prayerTimeApi.fetchDate(date)

    override suspend fun fetchAllTime(): List<PrayerTime> = prayerTimeApi.fetchAllTime()

    override fun setNearestPrayer(name: String) {
        appPrefs.nearestPrayer = name
    }

    override fun setPrayerTime(time: String) {
        appPrefs.prayerTime = time
    }

    override fun setPrayerTimeModel(prayerTime: PrayerTimeWallpaper) {
        val prayerTimeToString = Gson().toJson(prayerTime)
        appPrefs.prayerModel = prayerTimeToString
    }

}