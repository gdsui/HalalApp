package com.motionweb.halal.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.google.android.material.button.MaterialButton
import com.motionweb.halal.databinding.ErrorDialogLayoutBinding

class ErrorDialog @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attributeSet, defStyleAttr) {

    private val vb: ErrorDialogLayoutBinding by lazy(LazyThreadSafetyMode.NONE) {
        ErrorDialogLayoutBinding.inflate(
            LayoutInflater.from(context)
        )
    }

    fun setIcon(@DrawableRes resId: Int) {
        vb.ivIcon.setImageResource(resId)
    }

    fun setTitle(@StringRes title: Int) {
        vb.tvTitle.setText(title)
    }

    fun setTitle(title: String) {
        vb.tvTitle.text = title
    }

    fun setMessage(@StringRes message: Int) {
        vb.tvMessage.setText(message)
    }

    fun setMessage(message: String) {
        vb.tvMessage.text = message
    }

    fun action(action: () -> Unit, change: MaterialButton.() -> Unit = {}) {
        vb.btnAction.isVisible = true
        vb.btnAction.change()
        vb.btnAction.setOnClickListener { action.invoke() }
    }

}