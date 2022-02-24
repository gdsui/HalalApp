package com.motionweb.halal.ui.fragment.main


import android.os.CountDownTimer
import android.util.Log
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.motionweb.halal.R
import com.motionweb.halal.core.ui.fragment.CoreFragment
import com.motionweb.halal.data.network.prayertime.PrayerTime
import com.motionweb.halal.databinding.FragmentMainBinding
import com.motionweb.halal.ui.fragment.main.adapter.HalalBannerAdapter
import com.motionweb.halal.utils.Keys
import com.motionweb.halal.utils.prayerTime.PrayerTimeNames
import com.motionweb.halal.utils.prayerTime.PrayerTimeWallpaper
import com.motionweb.halal.utils.ZoomOutPageTransformer
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

@AndroidEntryPoint
class MainFragment : CoreFragment<FragmentMainBinding>() {

    private val vm: MainVM by viewModels()

    private var timer: CountDownTimer? = null

    private val bannerAdapter = HalalBannerAdapter()

    override fun createVB(): FragmentMainBinding =
        FragmentMainBinding.inflate(layoutInflater)

    override fun setupViews() {
        setupBanner()
        setupListeners()
        vm.fetchAllBanners()
        vm.fetchCurrentPrayerTime(getCurrentDate("yyyy-MM-dd"))
        subscribeToLiveData()
        vb.pvCalendar.setBottomText(getCurrentDate().toString())
    }

    override fun onResume() {
        super.onResume()
        shouldTimerRun()
    }

    private fun getCurrentDate(pattern: String = "EEE, d MMM ''yy"): String? {
        val date = SimpleDateFormat(pattern, Locale.getDefault())
        return date.format(Date())
    }

    private fun subscribeToLiveData() {
        vm.allBanners.observe(viewLifecycleOwner) {
            bannerAdapter.submitItems(it)
            setupIndicators()
            shouldTimerRun()
        }
        observePrayerTime()
    }

    private fun observePrayerTime() {
        vm.currentPrayerTime.observe(viewLifecycleOwner) { time ->
            val timesInLong = timeToLong(time)
            val currentDate = Calendar.getInstance().time.time
            var index = 0
            for (i in timesInLong.indices) {
                if (timesInLong[i] != null) {
                    if (currentDate <= timesInLong[i]!!) {
                        val prayerTime = convertLongToDate(timesInLong[i]).toString()
                        val nearestPrayer =
                            PrayerTimeNames.getPrayerName(requireContext(), i).toString()
                        vb.pvCalendar.setPrayerBottomText(prayerTime)
                        vb.pvCalendar.setPrayerTopText(nearestPrayer)
                        vm.setPrayerTime(prayerTime)
                        vm.setNearestPrayer(nearestPrayer)
                        index = i
                        break
                    } else {
                        val prayerTime = convertLongToDate(timesInLong[timesInLong.lastIndex]).toString()
                        val nearestPrayer =
                            PrayerTimeNames.getPrayerName(requireContext(), timesInLong.lastIndex).toString()
                        vb.pvCalendar.setPrayerBottomText(prayerTime)
                        vb.pvCalendar.setPrayerTopText(nearestPrayer)
                    }
                }
            }
            vm.setPrayerTimeModel(PrayerTimeWallpaper(time, index))
            vb.pvCalendar.setPrayerClickListener {
                findNavController().navigate(
                    R.id.prayerTimeFragment,
                    bundleOf(Keys.PRAYER_TIME to PrayerTimeWallpaper(time, index))
                )
            }
        }
    }

    private fun convertLongToDate(time: Long?): String? {
        if (time != null) {
            val date = Date(time)
            val format = SimpleDateFormat("HH:mm", Locale.getDefault())
            return format.format(date)
        }
        return null
    }

    private fun timeToLong(time: PrayerTime): List<Long?> {
        val times = mutableListOf<Long?>()
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        times.add(0, format.parse(time.date.mergeDateWithTime(time.firstTime))?.time)
        times.add(1, format.parse(time.date.mergeDateWithTime(time.secondTime))?.time)
        times.add(2, format.parse(time.date.mergeDateWithTime(time.thirdTime))?.time)
        times.add(3, format.parse(time.date.mergeDateWithTime(time.fourthTime))?.time)
        times.add(4, format.parse(time.date.mergeDateWithTime(time.fifthTime))?.time)
        times.add(5, format.parse(time.date.mergeDateWithTime(time.sixthTime))?.time)
        return times
    }

    private fun setupListeners() {
        vb.btnEcode.setOnClickListener {
            findNavController().navigate(R.id.eCodeFragment)
        }
        vb.btnFavourite.setOnClickListener {
            findNavController().navigate(R.id.favoriteFragment)
        }
        vb.btnRequests.setOnClickListener {
            findNavController().navigate(R.id.requestsFragment)
        }
        vb.pvCalendar.setCalendarClickListener {
            findNavController().navigate(R.id.islamicCalendarFragment)
        }
    }

    private fun setupBanner() {
        vb.vpBanner.adapter = bannerAdapter
        vb.vpBanner.registerOnPageChangeCallback(onBannerPageChange())
        vb.vpBanner.setPageTransformer(ZoomOutPageTransformer())
    }

    private fun startBannerTimer() {
        timer?.cancel()
        if (timer == null) {
            timer = object : CountDownTimer(Long.MAX_VALUE, 5000) {
                override fun onTick(p0: Long) {
                    showNextBanner()
                }

                override fun onFinish() {}
            }
        }
        timer?.start()
    }

    private fun shouldTimerRun() {
        if (bannerAdapter.itemCount == 0) {
            timer?.cancel()
        } else {
            startBannerTimer()
        }
    }

    private fun showNextBanner() {
        val bannerCount = bannerAdapter.itemCount
        if (bannerCount != 0) {
            val nextIndex = (vb.vpBanner.currentItem + 1) % bannerCount
            vb.vpBanner.currentItem = nextIndex
        } else {
            shouldTimerRun()
        }
    }

    private fun onBannerPageChange(): ViewPager2.OnPageChangeCallback {
        return (object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                setCurrentOnBoardingIndicator(position)
            }
        })
    }

    private fun setupIndicators() {
        val indicators = ArrayList<ImageView>()
        val layoutParams: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(16, 0, 0, 0)
        if (bannerAdapter.itemCount > 0) {
            for (i in 0 until bannerAdapter.itemCount) {
                indicators.add(ImageView(requireContext()))
                indicators[i].setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.ic_dot_active
                    )
                )
                indicators[i].layoutParams = layoutParams
                vb.indicatorView.addView(indicators[i])
            }
            setCurrentOnBoardingIndicator(0)
        } else return
    }

    private fun setCurrentOnBoardingIndicator(index: Int) {
        val childCount = vb.indicatorView.childCount
        for (i in 0 until childCount) {
            val imageView = vb.indicatorView.getChildAt(i) as ImageView
            imageView.setPadding(12, 0, 12, 0)
            if (i == index) {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.ic_dot_active
                    )
                )
            } else {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.ic_dot
                    )
                )
            }
        }
    }

    override fun onPause() {
        super.onPause()
        timer?.cancel()
    }

    companion object {
        fun newInstance(): MainFragment = MainFragment()
    }

}

private fun String.mergeDateWithTime(time: String): String =
    "$this $time"
