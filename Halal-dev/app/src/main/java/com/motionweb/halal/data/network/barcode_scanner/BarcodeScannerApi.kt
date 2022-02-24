package com.motionweb.halal.data.network.barcode_scanner

import retrofit2.http.GET
import retrofit2.http.Path

interface BarcodeScannerApi {

    @GET("companies/qrcode/{qr_code}")
    suspend fun findProductByBarcode(@Path("qr_code") code: Long): Barcode

}