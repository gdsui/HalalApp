package com.motionweb.halal.repository

import com.motionweb.halal.data.network.questions.Question
import com.motionweb.halal.data.network.questions.QuestionApi
import com.motionweb.halal.data.storage.sharedPref.AppPreferences
import javax.inject.Inject

interface QuestionsRepository {
    suspend fun fetchAllQuestions(): List<Question>
    suspend fun createQuestion(question: Question): Question
    val isAuthorized: Boolean
    val username: String
}

class QuestionsRepositoryImpl @Inject constructor(
    private val api: QuestionApi,
    appPrefs: AppPreferences
) : QuestionsRepository {

    override val isAuthorized: Boolean = appPrefs.isAuthorized

    override val username: String = appPrefs.username

    override suspend fun fetchAllQuestions() = api.fetchAllQuestions()

    override suspend fun createQuestion(question: Question): Question = api.createQuestion(question)

}