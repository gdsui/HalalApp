package com.motionweb.halal.ui.fragment.settings

import androidx.navigation.fragment.findNavController
import com.motionweb.halal.R
import com.motionweb.halal.core.ui.fragment.CoreFragment
import com.motionweb.halal.databinding.FragmentSettingsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : CoreFragment<FragmentSettingsBinding>() {

    override fun createVB(): FragmentSettingsBinding =
        FragmentSettingsBinding.inflate(layoutInflater)

    override fun setupViews() {
        super.setupViews()
        vb.nrCountry.setOnClickListener {
            findNavController().navigate(R.id.countryFragment2)
        }
        vb.nrLanguage.setOnClickListener {
            findNavController().navigate(R.id.languagePrefsFragment)
        }
    }

}