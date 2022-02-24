package com.motionweb.halal.ui.activity.main

import android.content.Context
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.motionweb.halal.R
import com.motionweb.halal.core.ui.activity.CoreActivity
import com.motionweb.halal.databinding.ActivityMainBinding
import com.motionweb.halal.ui.activity.scanner.ScannerActivity
import com.motionweb.halal.utils.NetworkManager
import com.motionweb.halal.utils.startActivityExt
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : CoreActivity<ActivityMainBinding>(),
    NetworkManager.ConnectivityReceiverListener {

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private val toolbar: Toolbar by lazy(LazyThreadSafetyMode.NONE) { vb.toolbar.toolbar }
    private val networkReceiver = NetworkManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.main_container) as NavHostFragment
        navController = navHostFragment.navController
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_nav)
        NavigationUI.setupWithNavController(bottomNavigationView, navHostFragment.navController)

        appBarConfiguration = AppBarConfiguration(navController.graph)
        setSupportActionBar(toolbar)
        setupActionBarWithNavController(navController, appBarConfiguration)
        registerNetworkReceiver()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.scanner -> {
                ScannerActivity.start(this)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onResume() {
        super.onResume()
        networkReceiver.connectivityReceiverListener = this
    }

    fun hideToolbar() {
        vb.toolbar.toolbar.isVisible = false
    }

    fun showToolbar() {
        vb.toolbar.toolbar.isVisible = true
    }

    fun hideBottomNav() {
        vb.bottomNav.isVisible = false
    }

    fun showBottomNav() {
        vb.bottomNav.isVisible = true
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        if (!isConnected) {
            networkAlertDialog()
        }
    }

    private fun networkAlertDialog() {
        val builder = AlertDialog.Builder(this)
        with(builder) {
            setTitle(R.string.network_lose_title)
            setMessage(R.string.network_lose_message)
            setPositiveButton(R.string.network_positive_btn_text, null)
            setIcon(R.drawable.ic_wifi_off)
        }.show()
    }

    private fun registerNetworkReceiver() {
        registerReceiver(networkReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    override fun onStop() {
        super.onStop()
        networkReceiver.connectivityReceiverListener = null
    }

    override fun getBinding() = ActivityMainBinding.inflate(layoutInflater)

    companion object {
        fun start(context: Context?) {
            context?.startActivityExt<MainActivity>()
        }
    }

}