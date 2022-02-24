package com.motionweb.halal.repository

import com.motionweb.halal.data.network.ecode.ECode
import com.motionweb.halal.data.network.ecode.ECodeApi
import com.motionweb.halal.data.storage.sharedPref.AppPreferences
import javax.inject.Inject

interface ECodeRepository {
    suspend fun fetchAllECodes(): List<ECode>
    suspend fun fetchECode(eCodeId: Int): ECode

    val prayerTime: String
    val nearestPrayer: String
}

class ECodeRepositoryImpl @Inject constructor(
    private val api: ECodeApi,
    private val appPrefs: AppPreferences
) : ECodeRepository {

    override suspend fun fetchAllECodes(): List<ECode> = api.fetchAllECodes()

    override suspend fun fetchECode(eCodeId: Int): ECode = api.fetchECode(eCodeId)

    override val prayerTime: String = appPrefs.prayerTime

    override val nearestPrayer: String = appPrefs.nearestPrayer

}