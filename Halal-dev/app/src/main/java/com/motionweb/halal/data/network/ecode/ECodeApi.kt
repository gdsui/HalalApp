package com.motionweb.halal.data.network.ecode

import retrofit2.http.GET
import retrofit2.http.Path

interface ECodeApi {

    @GET("codes/all")
    suspend fun fetchAllECodes(): List<ECode>

    @GET("codes/{id}")
    suspend fun fetchECode(@Path("id") eCodeId: Int): ECode

}