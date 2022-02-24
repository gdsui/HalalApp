package com.motionweb.halal.ui.onboarding.fragment.language

import com.motionweb.halal.core.ui.viewmodel.CoreVM
import com.motionweb.halal.data.storage.sharedPref.AppPreferences
import com.motionweb.halal.repository.LanguageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LanguageVM @Inject constructor(private val repository: LanguageRepository) : CoreVM() {

    val language: String = repository.language

    fun setLanguage(language: AppPreferences.Language) {
        repository.setLanguage(language)
    }

}