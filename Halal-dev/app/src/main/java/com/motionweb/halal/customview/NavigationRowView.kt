package com.motionweb.halal.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.view.isVisible
import com.motionweb.halal.R
import com.motionweb.halal.databinding.ViewNavigationRowBinding

class NavigationRowView(context: Context, private val attrib: AttributeSet) : LinearLayout(context, attrib) {

    private val vb = ViewNavigationRowBinding.inflate(LayoutInflater.from(context), this, true)


    init {
        context.theme.obtainStyledAttributes(
            attrib,
            R.styleable.NavigationRowView,
            0,
            0
        ).apply {
            try {
                setTitle(getString(R.styleable.NavigationRowView_title))
                showChevron(getBoolean(R.styleable.NavigationRowView_show_chevron, false))
                setIcon(getResourceId(R.styleable.NavigationRowView_icon, -1))
            } finally {
                recycle()
            }
        }
    }

    override fun setOnClickListener(l: OnClickListener?) {
        vb.root.setOnClickListener(l)
    }

    fun showChevron(value: Boolean) {
        vb.ivChevron.isVisible = value
    }

    fun setTitle(text: String?) {
        vb.tvTitle.text = text
    }

    fun setText(@StringRes textResId: Int) {
        vb.tvTitle.setText(textResId)
    }

    fun setIcon(@DrawableRes iconRes: Int) {
        if (iconRes == -1) return
        vb.ivIcon.setImageResource(iconRes)
    }

}