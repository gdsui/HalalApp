package com.motionweb.halal.customview

import android.content.Context
import android.util.AttributeSet
import androidx.preference.CheckBoxPreference
import androidx.preference.TwoStatePreference
import com.motionweb.halal.R

class RadioButtonPreference : CheckBoxPreference {

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        setView()
    }

    constructor(context: Context, attrs: AttributeSet? = null) : super(context, attrs) {
        setView()
    }

    private fun setView() {
        //layoutResource = R.layout.preference_widget_radiobutton
        widgetLayoutResource = R.layout.preference_widget_radiobutton
    }

    override fun onClick() {
        if (this.isChecked)
            return
        super.onClick()
    }
}