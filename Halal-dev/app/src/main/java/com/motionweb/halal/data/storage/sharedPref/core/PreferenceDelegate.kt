package com.motionweb.halal.data.storage.sharedPref.core

import android.content.SharedPreferences
import android.os.Parcelable
import android.util.Log
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class PreferenceDelegate<V>(
    private val pref: SharedPreferences,
    private val key: String,
    private val defaultValue: V
) : ReadWriteProperty<Any?, V> {

    override fun getValue(thisRef: Any?, property: KProperty<*>): V {
        with(pref) {
            return when (defaultValue) {
                is String -> (getString(key, defaultValue) as? V) ?: defaultValue
                is Int -> (getInt(key, defaultValue) as? V) ?: defaultValue
                is Long -> (getLong(key, defaultValue) as? V) ?: defaultValue
                is Float -> (getFloat(key, defaultValue) as? V) ?: defaultValue
                is Boolean -> (getBoolean(key, defaultValue) as? V) ?: defaultValue
                else -> throw IllegalStateException("Not found realization for $defaultValue")
            }
        }
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: V) {
        with(pref.edit()) {
            when (value) {
                is String -> putString(key, value)
                is Int -> putInt(key, value)
                is Long -> putLong(key, value)
                is Float -> putFloat(key, value)
                is Boolean -> putBoolean(key, value)
                else -> throw IllegalStateException("Not found realization for $defaultValue")
            }
            apply()
        }
    }

}