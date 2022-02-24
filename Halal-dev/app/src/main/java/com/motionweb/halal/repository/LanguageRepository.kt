package com.motionweb.halal.repository

import com.motionweb.halal.data.storage.sharedPref.AppPreferences
import javax.inject.Inject

interface LanguageRepository {
    val language: String
    fun setLanguage(language: AppPreferences.Language)
}

class LanguageRepositoryImpl @Inject constructor(
    private val prefs: AppPreferences
) : LanguageRepository {

    override val language: String
        get() = prefs.language

    override fun setLanguage(language: AppPreferences.Language) {
        prefs.setAppLanguage(language)
    }

}