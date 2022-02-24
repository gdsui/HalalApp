package com.motionweb.halal.ui.fragment.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.motionweb.halal.data.network.banner.Banner
import com.motionweb.halal.data.network.prayertime.PrayerTime
import com.motionweb.halal.repository.MainRepository
import com.motionweb.halal.utils.prayerTime.PrayerTimeWallpaper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainVM @Inject constructor(private val repository: MainRepository) : ViewModel() {

    private val _allBanners: MutableLiveData<List<Banner>> = MutableLiveData()
    val allBanners: LiveData<List<Banner>> get() = _allBanners

    private val _currentPrayerTime: MutableLiveData<PrayerTime> = MutableLiveData()
    val currentPrayerTime: LiveData<PrayerTime> get() = _currentPrayerTime

    fun fetchAllBanners() {
        viewModelScope.launch {
            try {
                _allBanners.value = repository.fetchAllBanners()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun fetchCurrentPrayerTime(date: String?) {
        if (date == null) return
        viewModelScope.launch {
            try {
                val response = repository.fetchCurrentDate(date)
                _currentPrayerTime.value = response
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun setPrayerTime(time: String) {
        repository.setPrayerTime(time)
    }

    fun setNearestPrayer(prayer: String) {
        repository.setNearestPrayer(prayer)
    }

    fun setPrayerTimeModel(time: PrayerTimeWallpaper) {
        repository.setPrayerTimeModel(time)
    }

}