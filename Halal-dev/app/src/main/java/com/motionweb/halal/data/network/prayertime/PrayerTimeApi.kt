package com.motionweb.halal.data.network.prayertime

import retrofit2.http.GET
import retrofit2.http.Path

interface PrayerTimeApi {

    @GET("times/time/all")
    suspend fun fetchAllTime(): List<PrayerTime>

    @GET("times/time/{date}")
    suspend fun fetchDate(@Path("date") date: String): PrayerTime

}