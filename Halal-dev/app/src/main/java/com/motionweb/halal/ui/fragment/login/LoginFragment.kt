package com.motionweb.halal.ui.fragment.login

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.motionweb.halal.R
import com.motionweb.halal.core.ui.fragment.CoreFragment
import com.motionweb.halal.databinding.FragmentLoginBinding
import com.motionweb.halal.ui.activity.main.MainActivity
import com.motionweb.halal.utils.centeredToast
import com.motionweb.halal.utils.parentActivity
import com.motionweb.halal.utils.toast
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginFragment : CoreFragment<FragmentLoginBinding>() {

    private val vm: LoginVM by viewModels()

    override fun createVB(): FragmentLoginBinding = FragmentLoginBinding.inflate(layoutInflater)

    override fun setupViews() {
        super.setupViews()
        (parentActivity as MainActivity).hideToolbar()
        (parentActivity as MainActivity).hideBottomNav()
        vb.btnLogin.setOnClickListener {
            if (checkEmailInput() && checkPasswordInput()) {
                vb.progress.isVisible = true
                vm.login(
                    vb.etEmail.text.toString(),
                    vb.etPassword.text.toString(),
                )
            }
        }
        vm.isLogin.observe(viewLifecycleOwner) { isLogin ->
            if (isLogin) {
                requireContext().toast(R.string.success_login)
                vb.progress.isVisible = false
                findNavController().navigate(
                    R.id.profileFragment,
                    null,
                    NavOptions.Builder().setPopUpTo(R.id.loginFragment, true).build()
                )
            } else {
                requireContext().toast(R.string.error_occurred)
                vb.progress.isVisible = false
            }
        }
        vb.btnRegister.setOnClickListener {
            findNavController().navigate(R.id.profileFragment)
        }
    }

    private fun checkEmailInput(): Boolean {
        if (vb.etEmail.text?.trim().isNullOrEmpty()) {
            requireContext().centeredToast(R.string.email_input_error)
            return false
        }
        return true
    }

    override fun onStop() {
        super.onStop()
        (parentActivity as MainActivity).showToolbar()
        (parentActivity as MainActivity).showBottomNav()
    }

    private fun checkPasswordInput(): Boolean {
        val password = vb.etPassword.text?.trim()
        if (password.isNullOrEmpty()) {
            requireContext().centeredToast(R.string.password_input_error)
            return false
        }
        return true
    }

}