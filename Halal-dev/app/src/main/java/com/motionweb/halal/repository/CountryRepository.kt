package com.motionweb.halal.repository

import com.motionweb.halal.data.storage.sharedPref.AppPreferences
import javax.inject.Inject

interface CountryRepository {
    val country: AppPreferences.Country
    fun setCountry(country: AppPreferences.Country)
}

class CountryRepositoryImpl @Inject constructor(
    private val appPreferences: AppPreferences
) : CountryRepository {

    // todo(нужно придумать что-то другое)
    override val country: AppPreferences.Country
        get() {
            return when (appPreferences.country) {
                AppPreferences.Country.ASIA.name -> AppPreferences.Country.ASIA
                AppPreferences.Country.CIS.name -> AppPreferences.Country.CIS
                AppPreferences.Country.EUROPE.name -> AppPreferences.Country.EUROPE
                AppPreferences.Country.AMERICA.name -> AppPreferences.Country.AMERICA
                AppPreferences.Country.AFRICA.name -> AppPreferences.Country.AFRICA
                else -> AppPreferences.Country.ASIA
            }
        }

    override fun setCountry(country: AppPreferences.Country) {
        appPreferences.setAppCountry(country)
    }
}