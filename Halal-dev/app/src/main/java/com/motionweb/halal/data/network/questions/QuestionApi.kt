package com.motionweb.halal.data.network.questions

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface QuestionApi {

    @GET("questions/all")
    suspend fun fetchAllQuestions(): List<Question>

    @GET("questions/{id}")
    suspend fun fetchQuestionById(@Path("id") id: Int): Question

    @POST("questions/all/")
    suspend fun createQuestion(@Body question: Question): Question
}