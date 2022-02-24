package com.motionweb.halal.repository

import com.motionweb.halal.data.storage.sharedPref.AppPreferences
import javax.inject.Inject

interface SplashRepository {
    val isFirstLaunch: Boolean
}

class SplashRepositoryImpl @Inject constructor(
    private val appPrefs: AppPreferences
): SplashRepository {

    override val isFirstLaunch: Boolean
        get() = appPrefs.isFirstLaunch

}