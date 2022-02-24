package com.motionweb.halal.ui.onboarding.activity

import android.content.Context
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.motionweb.halal.R
import com.motionweb.halal.core.ui.activity.CoreActivity
import com.motionweb.halal.databinding.ActivityOnboardingBinding
import com.motionweb.halal.utils.startActivityExt
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class OnboardingActivity : CoreActivity<ActivityOnboardingBinding>() {

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private val toolbar: Toolbar by lazy(LazyThreadSafetyMode.NONE) { vb.toolbar.toolbar }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.onboarding_container) as NavHostFragment
        navController = navHostFragment.navController
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setSupportActionBar(toolbar)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun getBinding() = ActivityOnboardingBinding.inflate(layoutInflater)

    companion object {
        fun start(context: Context?) {
            context?.startActivityExt<OnboardingActivity>()
        }
    }
}