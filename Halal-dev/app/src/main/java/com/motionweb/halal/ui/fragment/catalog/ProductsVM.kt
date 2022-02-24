package com.motionweb.halal.ui.fragment.catalog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.motionweb.halal.core.ui.viewmodel.CoreVM
import com.motionweb.halal.data.network.catalog.models.Product
import com.motionweb.halal.repository.CatalogRepository
import com.motionweb.halal.utils.ResultWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsVM @Inject constructor(
    private val repository: CatalogRepository
) : CoreVM() {

    private val _allProducts: MutableLiveData<ResultWrapper<List<Product>>> = MutableLiveData()
    val allProducts: LiveData<ResultWrapper<List<Product>>> get() = _allProducts

    val prayerTime: String = repository.prayerTime
    val nearestPrayer: String = repository.nearestPrayer

    fun fetchCompanyProducts(companyId: Int) {
        viewModelScope.launch {
            _allProducts.value = ResultWrapper.loading()
            try {
                val response = repository.fetchCompanyProducts(companyId)
                _allProducts.value = ResultWrapper.successOrEmpty(response)
            } catch (e: Exception) {
                e.printStackTrace()
                _allProducts.value = ResultWrapper.error(e)
            }
        }

    }
}