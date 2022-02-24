package com.motionweb.halal.utils

import android.annotation.TargetApi
import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import java.util.*

object LocaleService {

    fun updateBaseContextLocale(context: Context): Context {
        val language = getLanguageFromPreferences(context)
        val locale = Locale(language)
        Locale.setDefault(locale)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return updateResourcesLocale(context, locale)
        }
        return updateResourcesLocaleLegacy(context, locale)
    }

    @TargetApi(Build.VERSION_CODES.N)
    private fun updateResourcesLocale(
        context: Context,
        locale: Locale
    ): Context {
        val configuration: Configuration = context.resources.configuration
        configuration.setLocale(locale)
        return context.createConfigurationContext(configuration)
    }

    @Suppress(DEPRECATION)
    private fun updateResourcesLocaleLegacy(
        context: Context,
        locale: Locale
    ): Context {
        val resources: Resources = context.resources
        val configuration: Configuration = resources.configuration
        configuration.locale = locale
        resources.updateConfiguration(configuration, resources.displayMetrics)
        return context
    }

    private fun getLanguageFromPreferences(context: Context): String {
        val sharedPreferences = context.getSharedPreferences(PREF_DB_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.get(PREF_TITLE_LANG, LANGUAGE_DEFAULT)
    }
}