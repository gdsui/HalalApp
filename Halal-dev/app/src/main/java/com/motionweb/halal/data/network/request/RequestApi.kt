package com.motionweb.halal.data.network.request

import com.motionweb.halal.repository.RequestModel
import com.motionweb.halal.repository.RequestResponseModel
import retrofit2.http.Body
import retrofit2.http.POST

interface RequestApi {

    @POST("application/")
    suspend fun addRequest(@Body requestModel: RequestModel): RequestResponseModel

}