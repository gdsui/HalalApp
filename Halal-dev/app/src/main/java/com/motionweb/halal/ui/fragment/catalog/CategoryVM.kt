package com.motionweb.halal.ui.fragment.catalog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.motionweb.halal.core.ui.viewmodel.CoreVM
import com.motionweb.halal.data.network.catalog.models.Category
import com.motionweb.halal.repository.CatalogRepository
import com.motionweb.halal.utils.ResultWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryVM @Inject constructor(private val repository: CatalogRepository) : CoreVM() {

    private val _allCategories: MutableLiveData<ResultWrapper<List<Category>>> = MutableLiveData()
    val allCategories: LiveData<ResultWrapper<List<Category>>> get() = _allCategories

    val prayerTime: String = repository.prayerTime
    val nearestPrayer: String = repository.nearestPrayer

    fun fetchAllCategories() {
        _allCategories.value = ResultWrapper.loading()
        viewModelScope.launch {
            try {
                val response = repository.fetchAllCategories()
                _allCategories.value = ResultWrapper.successOrEmpty(response)
            } catch (e: Exception) {
                _allCategories.value = ResultWrapper.error(e)
            }
        }
    }

}