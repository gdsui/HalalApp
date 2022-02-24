package com.motionweb.halal.ui.onboarding.fragment.selection

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.motionweb.halal.R
import com.motionweb.halal.core.ui.fragment.CoreFragment
import com.motionweb.halal.databinding.FragmentSelectionBinding
import com.motionweb.halal.ui.activity.main.MainActivity
import com.motionweb.halal.utils.parentActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SelectionFragment : CoreFragment<FragmentSelectionBinding>() {

    private val vm: SelectionVM by viewModels()

    override fun setupViews() {
        vb.btnLanguage.setOnClickListener {
            findNavController().navigate(R.id.languagePrefsFragment)
        }

        vb.btnCountry.setOnClickListener {
            findNavController().navigate(R.id.countryFragment)
        }

        vb.btnNext.setOnClickListener {
            vm.setLaunch(true)
            MainActivity.start(parentActivity)
            parentActivity.finish()
        }
    }

    override fun onResume() {
        parentActivity.supportActionBar?.hide()
        super.onResume()
    }

    override fun createVB(): FragmentSelectionBinding =
        FragmentSelectionBinding.inflate(layoutInflater)

    override fun onStop() {
        super.onStop()
        parentActivity.supportActionBar?.show()
    }

    companion object {
        fun newInstance(): SelectionFragment = SelectionFragment()
    }
}