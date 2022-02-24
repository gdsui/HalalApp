package com.motionweb.halal.ui.fragment.calendar

import com.motionweb.halal.core.ui.fragment.CoreFragment
import com.motionweb.halal.databinding.FragmentIslamicCalendarBinding


class IslamicCalendarFragment : CoreFragment<FragmentIslamicCalendarBinding>() {

    override fun createVB(): FragmentIslamicCalendarBinding =
        FragmentIslamicCalendarBinding.inflate(layoutInflater)


    override fun setupViews() {
        /*val islamicCalendar = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            IslamicCalendar()
        } else {
            throw Exception("Version SDK")
        }
        println("Islamic time: ${islamicCalendar.time.time}")
        vb.calendar.date = islamicCalendar.time.time*/
        vb.calendar.isClickable = false
    }

}