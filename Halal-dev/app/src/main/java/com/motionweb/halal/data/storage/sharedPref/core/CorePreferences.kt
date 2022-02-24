package com.motionweb.halal.data.storage.sharedPref.core

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

abstract class CorePreferences(private val context: Context) {

    abstract val prefFileName: String

    val sharedPreference: SharedPreferences by lazy {
        context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE)
    }

    fun clear() = sharedPreference.edit { clear() }

}