package com.motionweb.halal.ui.onboarding.fragment.country

import android.util.Log
import androidx.fragment.app.viewModels
import com.motionweb.halal.core.ui.fragment.CoreFragment
import com.motionweb.halal.data.storage.sharedPref.AppPreferences
import com.motionweb.halal.databinding.FragmentCountryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CountryFragment : CoreFragment<FragmentCountryBinding>() {

    private val vm: CountryVM by viewModels()

    override fun createVB(): FragmentCountryBinding =
        FragmentCountryBinding.inflate(layoutInflater)

    override fun onStart() {
        super.onStart()
        vb.rbCis.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                vm.setCountry(AppPreferences.Country.CIS)
            }
        }
        vb.rbAsia.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                vm.setCountry(AppPreferences.Country.ASIA)
            }
        }
        vb.rbEurope.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                vm.setCountry(AppPreferences.Country.EUROPE)
            }
        }
        vb.rbUsa.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                vm.setCountry(AppPreferences.Country.AMERICA)
            }
        }
        vb.rbAfrica.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                vm.setCountry(AppPreferences.Country.AFRICA)
            }
        }
    }

    override fun setupViews() {
        setDefaultSettings()
    }

    private fun setDefaultSettings() {
        Log.e("TAG", "setDefaultSettings: ${vm.country}")
        when (vm.country) {
            AppPreferences.Country.CIS -> {
                vb.rbCis.isChecked = true
                vb.rbAsia.isChecked = false
                vb.rbEurope.isChecked = false
                vb.rbUsa.isChecked = false
                vb.rbAfrica.isChecked = false
            }
            AppPreferences.Country.ASIA -> {
                vb.rbCis.isChecked = false
                vb.rbAsia.isChecked = true
                vb.rbEurope.isChecked = false
                vb.rbUsa.isChecked = false
                vb.rbAfrica.isChecked = false
            }
            AppPreferences.Country.EUROPE -> {
                vb.rbCis.isChecked = false
                vb.rbAsia.isChecked = false
                vb.rbEurope.isChecked = true
                vb.rbUsa.isChecked = false
                vb.rbAfrica.isChecked = false
            }
            AppPreferences.Country.AMERICA -> {
                vb.rbCis.isChecked = false
                vb.rbAsia.isChecked = false
                vb.rbEurope.isChecked = false
                vb.rbUsa.isChecked = true
                vb.rbAfrica.isChecked = false
            }
            AppPreferences.Country.AFRICA -> {
                vb.rbCis.isChecked = false
                vb.rbAsia.isChecked = false
                vb.rbEurope.isChecked = false
                vb.rbUsa.isChecked = false
                vb.rbAfrica.isChecked = true
            }
        }
    }

    companion object {
        fun newInstance(): CountryFragment = CountryFragment()
    }
}