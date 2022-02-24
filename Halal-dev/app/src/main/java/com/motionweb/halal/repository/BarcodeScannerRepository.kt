package com.motionweb.halal.repository

import com.motionweb.halal.data.network.barcode_scanner.Barcode
import com.motionweb.halal.data.network.barcode_scanner.BarcodeScannerApi
import javax.inject.Inject

interface BarcodeScannerRepository {
    suspend fun findProductByBarcode(code: Long): Barcode
}

class BarcodeScannerRepositoryImpl @Inject constructor(private val api: BarcodeScannerApi) :
    BarcodeScannerRepository {

    override suspend fun findProductByBarcode(code: Long): Barcode =
        api.findProductByBarcode(code)

}