package com.motionweb.halal.utils.profile

import androidx.fragment.app.FragmentManager
import com.motionweb.halal.R
import com.motionweb.halal.ui.fragment.error.ErrorDialog

class ProfileDialogTypes private constructor() {

    companion object {
        fun getDialog(manager: FragmentManager, type: Types, tag: String? = null) {
            when (type) {
                Types.SUCCESSFUL_REGISTRATION -> {
                    ErrorDialog.show(manager, tag) {
                        icon = R.drawable.ic_check
                        title = R.string.success_registration
                    }
                }
                Types.USER_EXIST -> {
                    ErrorDialog.show(manager, tag) {
                        icon = R.drawable.ic_error
                        title = R.string.registration_error
                        message = R.string.user_exist
                    }
                }
                Types.ERROR -> {
                    ErrorDialog.show(manager, tag) {
                        icon = R.drawable.ic_error
                        title = R.string.unknown_error
                        message = R.string.try_again
                    }
                }
            }
        }
    }

    enum class Types {
        SUCCESSFUL_REGISTRATION, USER_EXIST, ERROR
    }

}