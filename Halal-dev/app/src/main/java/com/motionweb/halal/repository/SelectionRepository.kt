package com.motionweb.halal.repository

import com.motionweb.halal.data.storage.sharedPref.AppPreferences
import javax.inject.Inject

interface SelectionRepository {
    fun setLaunch(isLaunch: Boolean)
}

class SelectionRepositoryImpl @Inject constructor(
    private val appPreferences: AppPreferences
) : SelectionRepository {

    override fun setLaunch(isLaunch: Boolean) {
        appPreferences.isFirstLaunch = isLaunch
    }
}
