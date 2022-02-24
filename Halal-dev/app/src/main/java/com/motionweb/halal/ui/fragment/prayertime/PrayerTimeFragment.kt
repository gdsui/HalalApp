package com.motionweb.halal.ui.fragment.prayertime

import android.os.Handler
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.fragment.app.viewModels
import com.motionweb.halal.R
import com.motionweb.halal.core.ui.fragment.CoreFragment
import com.motionweb.halal.databinding.FragmentPrayerTimeBinding
import com.motionweb.halal.utils.prayerTime.PrayerTimeNames
import com.motionweb.halal.utils.getFormattedTime
import com.motionweb.halal.utils.prayerTime.PrayerWallpaper
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class PrayerTimeFragment : CoreFragment<FragmentPrayerTimeBinding>() {

    private val vm: PrayerTimeVM by viewModels()

    private var passedSeconds = 0
    private var calendar = Calendar.getInstance()
    private val updateHandler = Handler()

    override fun createVB(): FragmentPrayerTimeBinding =
        FragmentPrayerTimeBinding.inflate(layoutInflater)

    override fun setupViews() {
        setupTime()
    }

    private fun setupTime() {
        setupPrayerTime()
    }

    private fun setupPrayerTime() {
        val time = vm.prayerTime
        vb.ivPrImage.background = PrayerWallpaper.getWallpaper(requireContext(), time?.index)
        vb.apply {
            tvFirstTime.text = removeLastSecondsFromTime(time?.prayerTime?.firstTime)
            tvSecondTime.text = removeLastSecondsFromTime(time?.prayerTime?.secondTime)
            tvThirdTime.text = removeLastSecondsFromTime(time?.prayerTime?.thirdTime)
            tvFourthTime.text = removeLastSecondsFromTime(time?.prayerTime?.fifthTime)
            tvFifthTime.text = removeLastSecondsFromTime(time?.prayerTime?.fifthTime)
            tvSixthTime.text = removeLastSecondsFromTime(time?.prayerTime?.sixthTime)
            setupTimeColor(time?.index)
        }

    }

    private fun setupTimeColor(index: Int?) {
        when (index) {
            PrayerTimeNames.Names.FIRST.ordinal -> setColor(vb.tvFirstName to vb.tvFirstTime)
            PrayerTimeNames.Names.SECOND.ordinal -> setColor(vb.tvSecondName to vb.tvSecondTime)
            PrayerTimeNames.Names.THIRD.ordinal -> setColor(vb.tvThirdName to vb.tvThirdTime)
            PrayerTimeNames.Names.FOURTH.ordinal -> setColor(vb.tvFourthName to vb.tvFourthTime)
            PrayerTimeNames.Names.FIFTH.ordinal -> setColor(vb.tvFifthName to vb.tvFifthTime)
            PrayerTimeNames.Names.SIX.ordinal -> setColor(vb.tvSixthName to vb.tvSixthTime)
            else -> return
        }
    }

    private fun setColor(view: Pair<TextView, TextView>) {
        view.first.setTextColor(getColor(R.color.yellow))
        view.second.setTextColor(getColor(R.color.yellow))
    }

    @ColorInt
    private fun getColor(@ColorRes color: Int) = resources.getColor(color, null)

    override fun onResume() {
        super.onResume()
        setupDateTime()
    }

    override fun onPause() {
        super.onPause()
        updateHandler.removeCallbacksAndMessages(null)
    }

    private fun setupDateTime() {
        calendar = Calendar.getInstance()
        passedSeconds = fetchPassedSeconds()
        updateCurrentTime()
    }

    private fun updateCurrentTime() {
        vb.tvCurrentTime.text = requireContext().getFormattedTime(passedSeconds)
        updateHandler.postDelayed({
            passedSeconds++
            updateCurrentTime()
        }, ONE_SECOND)
    }


    private fun fetchPassedSeconds(): Int {
        val calendar = Calendar.getInstance()
        val isDaylightSavingActive = TimeZone.getDefault().inDaylightTime(Date())
        var offset = calendar.timeZone.rawOffset
        if (isDaylightSavingActive) {
            offset += TimeZone.getDefault().dstSavings
        }
        return ((calendar.timeInMillis + offset) / 1000).toInt()
    }

    // e.g 05:13:00 to 05:13
    private fun removeLastSecondsFromTime(time: String?): String {
        return time?.substring(0..time.length - 4) ?: ""
    }

    companion object {
        private const val ONE_SECOND: Long = 1000L
    }

}