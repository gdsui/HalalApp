package com.motionweb.halal.repository

import com.google.gson.annotations.SerializedName
import com.motionweb.halal.data.network.request.RequestApi
import javax.inject.Inject

interface RequestsRepository {
    suspend fun addRequest(requestModel: RequestModel): RequestResponseModel
}

class RequestsRepositoryImpl @Inject constructor(private val api: RequestApi) : RequestsRepository {

    override suspend fun addRequest(requestModel: RequestModel): RequestResponseModel =
        api.addRequest(requestModel)

}

data class RequestModel(
    @SerializedName("company_name")
    val companyName: String,
    val inn: String,
    @SerializedName("product_type")
    val productType: String,
    val name: String,
    val phone: String,
    val address: String
)

data class RequestResponseModel(
    val id: Int,
    @SerializedName("company_name")
    val companyName: String,
    val inn: String,
    @SerializedName("product_type")
    val productType: String,
    val name: String,
    val phone: String
)