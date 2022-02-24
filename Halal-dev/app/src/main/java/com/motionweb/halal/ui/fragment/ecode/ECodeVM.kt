package com.motionweb.halal.ui.fragment.ecode

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.motionweb.halal.core.ui.viewmodel.CoreVM
import com.motionweb.halal.data.network.ecode.ECode
import com.motionweb.halal.repository.ECodeRepository
import com.motionweb.halal.utils.ResultWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ECodeVM @Inject constructor(private val repository: ECodeRepository) : CoreVM() {

    private val _allECodes: MutableLiveData<ResultWrapper<List<ECode>>> = MutableLiveData()
    val allECodes: LiveData<ResultWrapper<List<ECode>>> get() = _allECodes

    val prayerTime: String = repository.prayerTime
    val nearestPrayer: String = repository.nearestPrayer

    fun fetchAllECodes() {
        viewModelScope.launch {
            try {
                _allECodes.value = ResultWrapper.loading()
                val response = repository.fetchAllECodes()
                if (response.isNotEmpty()) {
                    sortCodes(response)
                }
            } catch (e: Exception) {
                _allECodes.value = ResultWrapper.error(e)
                e.printStackTrace()
            }
        }
    }

    private fun sortCodes(eCodes: List<ECode>) {
        var sortedCodes: List<ECode>
        viewModelScope.launch(Dispatchers.Default) {
            sortedCodes = eCodes.sortedBy {
                (it.code).replace("[^0-9]".toRegex(), "").toInt()
            }
            withContext(Dispatchers.Main) {
                _allECodes.value = ResultWrapper.success(sortedCodes)
            }
        }
    }

}