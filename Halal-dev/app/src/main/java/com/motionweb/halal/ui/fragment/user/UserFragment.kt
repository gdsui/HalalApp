package com.motionweb.halal.ui.fragment.user

import com.motionweb.halal.core.ui.fragment.CoreFragment
import com.motionweb.halal.databinding.FragmentUserBinding

class UserFragment : CoreFragment<FragmentUserBinding>() {

    override fun createVB(): FragmentUserBinding = FragmentUserBinding.inflate(layoutInflater)


}