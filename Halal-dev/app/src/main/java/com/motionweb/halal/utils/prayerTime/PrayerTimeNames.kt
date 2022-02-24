package com.motionweb.halal.utils.prayerTime

import android.content.Context
import android.os.Parcelable
import androidx.annotation.StringRes
import com.motionweb.halal.R
import kotlinx.parcelize.Parcelize


class PrayerTimeNames private constructor() {

    companion object {
        fun getPrayerName(context: Context, index: Int): String? {
            return when (index) {
                Names.FIRST.ordinal -> context.getString(Names.FIRST.prName)
                Names.SECOND.ordinal -> context.getString(Names.SECOND.prName)
                Names.THIRD.ordinal -> context.getString(Names.THIRD.prName)
                Names.FOURTH.ordinal -> context.getString(Names.FOURTH.prName)
                Names.FIFTH.ordinal -> context.getString(Names.FIFTH.prName)
                Names.SIX.ordinal -> context.getString(Names.SIX.prName)
                else -> null
            }
        }
    }

    @Parcelize
    enum class Names(@StringRes val prName: Int) : Parcelable {
        FIRST(R.string.first_prayer_name), SECOND(R.string.second_prayer_name),
        THIRD(R.string.third_prayer_name), FOURTH(R.string.fourth_prayer_name),
        FIFTH(R.string.fifth_prayer_name), SIX(R.string.sixth_prayer_name)
    }
}