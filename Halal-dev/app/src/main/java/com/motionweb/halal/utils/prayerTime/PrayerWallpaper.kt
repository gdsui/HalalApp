package com.motionweb.halal.utils.prayerTime

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import com.motionweb.halal.R

class PrayerWallpaper private constructor() {

    companion object {
        fun getWallpaper(context: Context, index: Int?): Drawable? {
            return when (index) {
                Wallpaper.FIRST.ordinal -> context.getDrawableRes(Wallpaper.FIRST.resId)
                Wallpaper.SECOND.ordinal -> context.getDrawableRes(Wallpaper.SECOND.resId)
                Wallpaper.THIRD.ordinal -> context.getDrawableRes(Wallpaper.THIRD.resId)
                Wallpaper.FOURTH.ordinal -> context.getDrawableRes(Wallpaper.FOURTH.resId)
                Wallpaper.FIFTH.ordinal -> context.getDrawableRes(Wallpaper.FIFTH.resId)
                Wallpaper.SIXTH.ordinal -> context.getDrawableRes(Wallpaper.SIXTH.resId)
                else -> null
            }
        }
    }

    private enum class Wallpaper(@DrawableRes val resId: Int) {
        FIRST(R.drawable.prayer_time_first_background),
        SECOND(R.drawable.prayer_time_second_background),
        THIRD(R.drawable.prayer_time_third_background),
        FOURTH(R.drawable.prayer_time_fourth_background),
        FIFTH(R.drawable.prayer_time_fifth_background),
        SIXTH(R.drawable.prayer_time_sixth_backround)
    }
}

private fun Context.getDrawableRes(@DrawableRes resId: Int): Drawable? =
    AppCompatResources.getDrawable(this, resId)