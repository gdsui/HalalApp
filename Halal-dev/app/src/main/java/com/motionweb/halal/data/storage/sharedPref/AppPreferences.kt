package com.motionweb.halal.data.storage.sharedPref

import android.content.Context
import android.util.Log
import com.motionweb.halal.data.storage.sharedPref.AppPreferences.PrefKeys.ACCESS_TOKEN
import com.motionweb.halal.data.storage.sharedPref.AppPreferences.PrefKeys.AVATAR_PATH
import com.motionweb.halal.data.storage.sharedPref.AppPreferences.PrefKeys.COUNTRY
import com.motionweb.halal.data.storage.sharedPref.AppPreferences.PrefKeys.EMAIL
import com.motionweb.halal.data.storage.sharedPref.AppPreferences.PrefKeys.FAVORITE
import com.motionweb.halal.data.storage.sharedPref.AppPreferences.PrefKeys.FIRST_LAUNCH
import com.motionweb.halal.data.storage.sharedPref.AppPreferences.PrefKeys.GENDER
import com.motionweb.halal.data.storage.sharedPref.AppPreferences.PrefKeys.IS_AUTHORIZED
import com.motionweb.halal.data.storage.sharedPref.AppPreferences.PrefKeys.LANGUAGE
import com.motionweb.halal.data.storage.sharedPref.AppPreferences.PrefKeys.NEAREST_PRAYER_NAME
import com.motionweb.halal.data.storage.sharedPref.AppPreferences.PrefKeys.PASSWORD
import com.motionweb.halal.data.storage.sharedPref.AppPreferences.PrefKeys.PRAYER_MODEL
import com.motionweb.halal.data.storage.sharedPref.AppPreferences.PrefKeys.PRAYER_TIME
import com.motionweb.halal.data.storage.sharedPref.AppPreferences.PrefKeys.REFRESH_TOKEN
import com.motionweb.halal.data.storage.sharedPref.AppPreferences.PrefKeys.USERNAME
import com.motionweb.halal.data.storage.sharedPref.AppPreferences.PrefKeys.USER_PHONE
import com.motionweb.halal.data.storage.sharedPref.core.CorePreferences
import com.motionweb.halal.data.storage.sharedPref.core.PreferenceDelegate
import com.motionweb.halal.utils.DefaultValues
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppPreferences @Inject constructor(@ApplicationContext context: Context) :
    CorePreferences(context) {

    override val prefFileName: String = "com.motionweb.halal.appPref"

    var language: String by PreferenceDelegate(sharedPreference, LANGUAGE, Language.RUSSIAN.name)
        private set

    var country: String by PreferenceDelegate(sharedPreference, COUNTRY, Country.ASIA.name)
        private set

    var isFirstLaunch: Boolean by PreferenceDelegate(sharedPreference, FIRST_LAUNCH, false)

    var prayerTime: String by PreferenceDelegate(sharedPreference, PRAYER_TIME, "")
    var nearestPrayer: String by PreferenceDelegate(sharedPreference, NEAREST_PRAYER_NAME, "")

    var isAuthorized: Boolean by PreferenceDelegate(sharedPreference, IS_AUTHORIZED, false)

    var username: String by PreferenceDelegate(sharedPreference, USERNAME, "")
    var userPhone: String by PreferenceDelegate(sharedPreference, USER_PHONE, "")
    var prayerModel: String by PreferenceDelegate(sharedPreference, PRAYER_MODEL, "")
    var gender: String by PreferenceDelegate(sharedPreference, GENDER, "")
    var email: String by PreferenceDelegate(sharedPreference, EMAIL, "")
    var password: String by PreferenceDelegate(sharedPreference, PASSWORD, "")

    var avatarPath: String by PreferenceDelegate(
        sharedPreference,
        AVATAR_PATH,
        DefaultValues.avatarPath(context)
    )

    var accessToken: String by PreferenceDelegate(sharedPreference, ACCESS_TOKEN, "")
    var refreshToken: String by PreferenceDelegate(sharedPreference, REFRESH_TOKEN, "")
    var favorites: String by PreferenceDelegate(sharedPreference, FAVORITE, "")

    fun setAppLanguage(language: Language) {
        this.language = language.name
    }

    fun setAppCountry(country: Country) {
        Log.e("TAG", "setAppCountry: ${country.name}")
        this.country = country.name
    }

    fun clearUserData() {
        clear()
    }

    enum class Language(val language: String) {
        KYRGYZ(AppPreferences.KYRGYZ), RUSSIAN(AppPreferences.RUSSIAN)
    }

    enum class Country(val country: String) {
        CIS("CIS"), ASIA("Asia"), AMERICA("America"), EUROPE("Europe"),
        AFRICA("Africa")
    }

    private object PrefKeys {
        const val LANGUAGE: String = "SELECTED_LANGUAGE"
        const val COUNTRY: String = "SELECTED_COUNTRY"
        const val FIRST_LAUNCH: String = "FIRST_LAUNCH"
        const val PRAYER_TIME: String = "PRAYER_TIME"
        const val NEAREST_PRAYER_NAME: String = "NEAREST_PRAYER_NAME"
        const val IS_AUTHORIZED: String = "IS_AUTHORIZED"
        const val USERNAME: String = "USERNAME"
        const val USER_PHONE: String = "USER_PHONE"
        const val GENDER: String = "GENDER"
        const val EMAIL: String = "EMAIL"
        const val PASSWORD: String = "PASSWORD"
        const val AVATAR_PATH: String = "AVATAR_PATH"
        const val REFRESH_TOKEN: String = "REFRESH_TOKEN"
        const val ACCESS_TOKEN: String = "ACCESS_TOKEN"
        const val PRAYER_MODEL: String = "PrayerModel"
        const val FAVORITE: String = "FAVORITE"
    }

    companion object {
        const val KYRGYZ: String = "kyrgyz"
        const val RUSSIAN: String = "russian"
    }

}