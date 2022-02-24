package com.motionweb.halal.ui.fragment.requests

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.motionweb.halal.core.ui.viewmodel.CoreVM
import com.motionweb.halal.repository.RequestModel
import com.motionweb.halal.repository.RequestResponseModel
import com.motionweb.halal.repository.RequestsRepository
import com.motionweb.halal.utils.ResultWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RequestsVM @Inject constructor(private val repository: RequestsRepository) : CoreVM() {

    val request: MutableLiveData<ResultWrapper<RequestResponseModel>> = MutableLiveData()

    fun addRequest(requestModel: RequestModel) {
        request.value = ResultWrapper.loading()
        viewModelScope.launch {
            try {
                val response = repository.addRequest(requestModel)
                request.value = ResultWrapper.success(response)
            } catch (e: Exception) {
                e.printStackTrace()
                request.value = ResultWrapper.error(e)
            }
        }
    }

}