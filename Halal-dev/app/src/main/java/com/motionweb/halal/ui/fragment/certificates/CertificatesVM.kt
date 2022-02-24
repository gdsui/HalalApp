package com.motionweb.halal.ui.fragment.certificates

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.motionweb.halal.core.ui.viewmodel.CoreVM
import com.motionweb.halal.data.network.certificates.Certificate
import com.motionweb.halal.repository.CertificatesRepository
import com.motionweb.halal.utils.ResultWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CertificatesVM @Inject constructor(
    private val repository: CertificatesRepository
) : CoreVM() {

    private val _certificates: MutableLiveData<ResultWrapper<List<Certificate>>> = MutableLiveData()
    val certificates: LiveData<ResultWrapper<List<Certificate>>> get() = _certificates

    fun fetchCertificates() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.fetchCertificates()
                _certificates.postValue(ResultWrapper.success(response))
            } catch (e: Exception) {
                _certificates.postValue(ResultWrapper.error(e))
            }
        }
    }

}