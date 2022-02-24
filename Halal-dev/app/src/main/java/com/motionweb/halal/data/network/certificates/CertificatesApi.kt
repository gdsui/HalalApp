package com.motionweb.halal.data.network.certificates

import retrofit2.http.GET
import retrofit2.http.Path

interface CertificatesApi {

    @GET("certificates")
    suspend fun fetchCertificates(): List<Certificate>

    @GET("certificates/{id}")
    suspend fun fetchCertificateById(@Path("id") id: Int): Certificate

}