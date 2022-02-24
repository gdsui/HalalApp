package com.motionweb.halal.ui.fragment.prayertime

import com.motionweb.halal.core.ui.viewmodel.CoreVM
import com.motionweb.halal.repository.PrayerTimeRepository
import com.motionweb.halal.utils.prayerTime.PrayerTimeWallpaper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PrayerTimeVM @Inject constructor(private val repository: PrayerTimeRepository) : CoreVM() {

    val prayerTime: PrayerTimeWallpaper? get() = repository.getPrayerTime()

}