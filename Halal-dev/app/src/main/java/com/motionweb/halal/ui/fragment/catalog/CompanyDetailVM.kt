package com.motionweb.halal.ui.fragment.catalog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.motionweb.halal.core.ui.viewmodel.CoreVM
import com.motionweb.halal.data.network.catalog.models.Company
import com.motionweb.halal.data.network.catalog.models.CompanyDetail
import com.motionweb.halal.repository.CatalogRepository
import com.motionweb.halal.utils.ResultWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompanyDetailVM @Inject constructor(private val repository: CatalogRepository) : CoreVM() {

    private val _company: MutableLiveData<ResultWrapper<CompanyDetail>> = MutableLiveData()
    val company: LiveData<ResultWrapper<CompanyDetail>> get() = _company

    val prayerTime: String = repository.prayerTime
    val nearestPrayer: String = repository.nearestPrayer

    fun fetchCompanyDetail(companyId: Int) {
        viewModelScope.launch {
            _company.value = ResultWrapper.loading()
            try {
                val response = repository.fetchCompanyDetail(companyId)
                _company.value = ResultWrapper.success(response)
            } catch (e: Exception) {
                _company.value = ResultWrapper.error(e)
                e.printStackTrace()
            }
        }
    }

}