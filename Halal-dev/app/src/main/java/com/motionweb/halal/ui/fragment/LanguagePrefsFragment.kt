package com.motionweb.halal.ui.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.motionweb.halal.R
import com.motionweb.halal.customview.RadioButtonPreference
import com.motionweb.halal.utils.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LanguagePrefsFragment : PreferenceFragmentCompat(), Preference.OnPreferenceClickListener {

    private var oldCheckedPreference: RadioButtonPreference? = null

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preference_layout, rootKey)
        val sharedPreferences =
            requireContext().getSharedPreferences(PREF_DB_NAME, Context.MODE_PRIVATE)
        val language = sharedPreferences.get(PREF_TITLE_LANG, LANGUAGE_DEFAULT)
        setDefaultLang(language)
        findPreference<RadioButtonPreference>("languageRU")?.onPreferenceClickListener = this
        findPreference<RadioButtonPreference>("languageKG")?.onPreferenceClickListener = this
    }

    private fun setDefaultLang(lang: String) {
        when (lang) {
            "ru" -> {
                findPreference<RadioButtonPreference>("languageRU")?.apply {
                    updateCheckedRadioButton(this)
                }
            }
            "ky" -> {
                findPreference<RadioButtonPreference>("languageKG")?.apply {
                    updateCheckedRadioButton(this)
                }
            }
            else -> return
        }
    }

    private fun handleChangeLanguage(newLang: String) {
        val langCode = when (newLang) {
            "languageRU" -> LANGUAGE_RU
            "languageKG" -> LANGUAGE_KG
            else -> LANGUAGE_RU
        }
        requireContext()
            .getSharedPreferences(PREF_DB_NAME, Context.MODE_PRIVATE).apply {
                put(PREF_TITLE_LANG, langCode)
            }
        requireActivity().recreate()
    }

    private fun updateCheckedRadioButton(radioButtonPreference: RadioButtonPreference) {
        //Uncheck the previous selected button if there is.
        oldCheckedPreference?.isChecked = false
        radioButtonPreference.isChecked = true
        oldCheckedPreference = radioButtonPreference
    }

    override fun onPreferenceClick(preference: Preference?): Boolean {
        if (preference is RadioButtonPreference) {
            updateCheckedRadioButton(preference)
            handleChangeLanguage(preference.key)
        }
        return true
    }

    companion object {
        const val LANGUAGE_RU = "ru"
        const val LANGUAGE_KG = "ky"
    }

}