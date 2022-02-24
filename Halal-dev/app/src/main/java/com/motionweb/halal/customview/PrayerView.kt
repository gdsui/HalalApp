package com.motionweb.halal.customview

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.constraintlayout.widget.ConstraintLayout
import com.motionweb.halal.R
import com.motionweb.halal.databinding.PrayerViewBinding

class PrayerView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attributeSet, defStyleAttr) {


    private val vb = PrayerViewBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        context.theme.obtainStyledAttributes(
            attributeSet,
            R.styleable.PrayerView,
            0,
            0
        ).apply {
            try {
                setTopText(getString(R.styleable.PrayerView_top_text).toString())
                setBottomText(getString(R.styleable.PrayerView_bottom_text).toString())
                setIcon(getResourceId(R.styleable.PrayerView_icon, R.drawable.ic_calendar))
                setPrayerBottomText(getString(R.styleable.PrayerView_prayer_bottom_text).toString())
                setPrayerTopText(getString(R.styleable.PrayerView_prayer_top_text).toString())
                setPrayerIcon(getResourceId(R.styleable.PrayerView_prayer_icon, R.drawable.ic_clock))
            } finally {
                recycle()
            }
        }
    }

    fun setTopText(text: String) {
        vb.tvTopText.text = text
    }

    fun setTopText(@StringRes text: Int) {
        vb.tvTopText.setText(text)
    }

    fun setBottomText(text: String) {
        vb.tvBottomText.text = text
    }

    fun setIcon(@DrawableRes icon: Int) {
        vb.ivIcon.setImageResource(icon)
    }

    fun setPrayerTopText(text: String) {
        vb.tvPrayerTopText.text = text
    }

    fun setPrayerTopText(@StringRes resId: Int) {
        vb.tvPrayerTopText.setText(resId)
    }

    fun setPrayerBottomText(text: String) {
        vb.tvPrayerBottomText.text = text
    }

    fun setPrayerBottomText(@StringRes resId: Int) {
        vb.tvPrayerBottomText.setText(resId)
    }

    fun setPrayerIcon(@DrawableRes icon: Int) {
        vb.ivSecondIcon.setImageResource(icon)
    }

    fun setPrayerClickListener(listener: OnClickListener) {
        vb.clPrayer.setOnClickListener(listener)
    }

    fun setCalendarClickListener(listener: OnClickListener) {
        vb.clCalendar.setOnClickListener(listener)
    }

}