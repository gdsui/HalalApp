package com.motionweb.halal.ui.activity.scanner.dialog

import androidx.fragment.app.FragmentManager
import com.motionweb.halal.R

class ScannerResultDialog private constructor() {

    companion object {
        fun showDialog(
            manager: FragmentManager,
            type: Type,
            status: String,
            productName: String?,
            tag: String? = null
        ) {
            when (type) {
                Type.Found -> {
                    BarcodeStatusDialog.show(manager, tag) {
                        icon = R.drawable.ic_check
                        title = status
                        message = productName
                    }
                }
                Type.NotFound -> {
                    BarcodeStatusDialog.show(manager, tag) {
                        icon = R.drawable.ic_error
                        titleRes = R.string.product_non_certified
                    }
                }
            }
        }
    }

    enum class Type {
        Found, NotFound
    }
}