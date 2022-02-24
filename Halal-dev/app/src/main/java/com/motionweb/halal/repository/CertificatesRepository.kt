package com.motionweb.halal.repository

import com.motionweb.halal.data.network.certificates.Certificate
import com.motionweb.halal.data.network.certificates.CertificatesApi
import javax.inject.Inject

interface CertificatesRepository {
    suspend fun fetchCertificates(): List<Certificate>
    suspend fun fetchCertificateById(id: Int): Certificate
}

class CertificatesRepositoryImpl @Inject constructor(
    private val api: CertificatesApi
) : CertificatesRepository {

    override suspend fun fetchCertificates(): List<Certificate> =
        api.fetchCertificates()

    override suspend fun fetchCertificateById(id: Int): Certificate =
        api.fetchCertificateById(id)

}