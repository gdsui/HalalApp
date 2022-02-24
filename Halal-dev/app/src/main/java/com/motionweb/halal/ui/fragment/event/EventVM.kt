package com.motionweb.halal.ui.fragment.event

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.motionweb.halal.data.network.banner.Banner
import com.motionweb.halal.data.network.event.Event
import com.motionweb.halal.repository.MainRepository
import com.motionweb.halal.utils.ResultWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventVM @Inject constructor(private val repository: MainRepository) : ViewModel() {

    private val _events: MutableLiveData<ResultWrapper<List<Banner>>> = MutableLiveData()
    val events: LiveData<ResultWrapper<List<Banner>>> = _events


    fun fetchEvents() {
        viewModelScope.launch {
            try {
                _events.value = ResultWrapper.loading()
                val response = repository.fetchAllBanners()
                if (response.isNotEmpty()) {
                    _events.value = ResultWrapper.success(response)
                }
            } catch (e: Exception) {
                _events.value = ResultWrapper.error(e)
                e.printStackTrace()
            }
        }
    }

}