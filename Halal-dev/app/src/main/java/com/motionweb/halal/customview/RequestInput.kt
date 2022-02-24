package com.motionweb.halal.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.StringRes
import androidx.constraintlayout.widget.ConstraintLayout
import com.motionweb.halal.R
import com.motionweb.halal.databinding.RequestInputBinding

class RequestInput(context: Context, attributeSet: AttributeSet) :
    ConstraintLayout(context, attributeSet) {

    private val vb: RequestInputBinding =
        RequestInputBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        context.theme.obtainStyledAttributes(
            attributeSet,
            R.styleable.RequestInput,
            0,
            0
        ).apply {
            try {
                setLabelText(getString(R.styleable.RequestInput_label_text))
            } finally {
                recycle()
            }
        }
    }

    fun setLabelText(text: String?) {
        vb.tvLabel.text = text
    }

    fun setLabelText(@StringRes idRes: Int) {
        vb.tvLabel.setText(idRes)
    }

    fun setInputText(text: String) {
        vb.etMain.setText(text)
    }

    fun fetchText(): String = vb.etMain.text.toString()

}