package com.motionweb.halal.ui.onboarding.fragment.country

import android.util.Log
import com.motionweb.halal.core.ui.viewmodel.CoreVM
import com.motionweb.halal.data.storage.sharedPref.AppPreferences
import com.motionweb.halal.repository.CountryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CountryVM @Inject constructor(private val repository: CountryRepository) : CoreVM() {

    val country: AppPreferences.Country get() = repository.country

    fun setCountry(country: AppPreferences.Country) {
        Log.e("TAG", "setCountry: $country")
        repository.setCountry(country)
    }

}