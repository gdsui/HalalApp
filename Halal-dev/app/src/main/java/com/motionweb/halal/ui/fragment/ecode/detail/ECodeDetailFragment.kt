package com.motionweb.halal.ui.fragment.ecode.detail

import androidx.navigation.fragment.navArgs
import com.motionweb.halal.core.ui.fragment.CoreFragment
import com.motionweb.halal.databinding.FragmentEcodeDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ECodeDetailFragment : CoreFragment<FragmentEcodeDetailBinding>() {

    private val args: ECodeDetailFragmentArgs by navArgs()

    override fun createVB(): FragmentEcodeDetailBinding =
        FragmentEcodeDetailBinding.inflate(layoutInflater)

    override fun setupViews() {
        val ecode = args.ecode
        vb.apply {
            tvName.text = ecode.code
            tvDescription.text = ecode.title
            tvHarmfulDescription.text = ecode.damageDescription
            tvUsefulDescription.text = ecode.usefulDescription
        }
        val type = ecode.toFoodType()
        if (type != null) {
            vb.foodType.setFoodType(type)
        }
    }
}