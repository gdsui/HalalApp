package com.motionweb.halal.ui.fragment.about

import com.motionweb.halal.core.ui.fragment.CoreFragment
import com.motionweb.halal.databinding.FragmentAboutBinding
import com.motionweb.halal.ui.activity.main.MainActivity
import com.motionweb.halal.utils.parentActivity

class AboutFragment : CoreFragment<FragmentAboutBinding>() {

    override fun createVB(): FragmentAboutBinding =
        FragmentAboutBinding.inflate(layoutInflater)

}