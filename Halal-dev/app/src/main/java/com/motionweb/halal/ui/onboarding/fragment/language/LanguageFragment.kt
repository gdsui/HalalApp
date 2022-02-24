package com.motionweb.halal.ui.onboarding.fragment.language

import androidx.fragment.app.viewModels
import com.motionweb.halal.core.ui.fragment.CoreFragment
import com.motionweb.halal.data.storage.sharedPref.AppPreferences
import com.motionweb.halal.databinding.FragmentLanguageBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LanguageFragment : CoreFragment<FragmentLanguageBinding>() {

    private val vm: LanguageVM by viewModels()

    override fun createVB(): FragmentLanguageBinding =
        FragmentLanguageBinding.inflate(layoutInflater)

    override fun setupViews() {
        getDefaultSettings()
        vb.rbKyrgyz.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                vm.setLanguage(AppPreferences.Language.RUSSIAN)
            }
        }
        vb.rbRussian.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                vm.setLanguage(AppPreferences.Language.RUSSIAN)
            }
        }
    }

    private fun getDefaultSettings() {
        if (vm.language == AppPreferences.KYRGYZ) {
            vb.rbKyrgyz.isChecked = true
            vb.rbRussian.isChecked = false
            return
        }
        vb.rbRussian.isChecked = true
        vb.rbKyrgyz.isChecked = false
    }

    companion object {
        fun newInstance(): LanguageFragment = LanguageFragment()
    }
}