package com.motionweb.halal.data.network.banner

import retrofit2.http.GET
import retrofit2.http.Path

interface BannerApi {

    @GET("banners/all")
    suspend fun fetchAllBanners(): List<Banner>

    @GET("banners/{id}")
    suspend fun fetchBanner(@Path("id") bannerId: Int): Banner

}