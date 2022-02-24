package com.motionweb.halal.ui.fragment.questions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.motionweb.halal.data.network.questions.Question
import com.motionweb.halal.repository.QuestionsRepository
import com.motionweb.halal.utils.ResultWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuestionAnswerVM @Inject constructor(private val repo: QuestionsRepository) : ViewModel() {

    private val _questions: MutableLiveData<ResultWrapper<List<Question>>> = MutableLiveData()
    val questions: LiveData<ResultWrapper<List<Question>>> = _questions

    private val _questionIsSent: MutableLiveData<Boolean> = MutableLiveData()
    val questionIsSent: LiveData<Boolean> = _questionIsSent

    val isAuthorized: Boolean get() = repo.isAuthorized
    private val username: String get() = repo.username

    fun fetchAllQuestions() {
        viewModelScope.launch {
            try {
                _questions.value = ResultWrapper.loading()
                val response = repo.fetchAllQuestions()
                if (response.isNotEmpty()) {
                    _questions.value = ResultWrapper.success(response)
                }
            } catch (e: Exception) {
                _questions.value = ResultWrapper.error(e)
            }
        }
    }

    fun createQuestion(text: String) {
        val question = Question(question = text, author = username, answer = null)
        viewModelScope.launch {
            try {
                val response = repo.createQuestion(question)
                if (response.id != null) {
                    _questionIsSent.value = true
                }
            } catch (e: Exception) {
                _questionIsSent.value = false
                e.printStackTrace()
            }
        }
    }
}