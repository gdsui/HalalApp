package com.motionweb.halal.ui.activity.splash

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.motionweb.halal.ui.activity.main.MainActivity
import com.motionweb.halal.ui.onboarding.activity.OnboardingActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private val vm: SplashVM by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (vm.isFirstLaunch) {
            MainActivity.start(this)
        } else {
            OnboardingActivity.start(this)
        }
        finish()
    }

}