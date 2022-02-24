package com.motionweb.halal.ui.activity.scanner

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.motionweb.halal.R
import com.motionweb.halal.core.ui.activity.CoreActivity
import com.motionweb.halal.databinding.ActivityScannerBinding
import com.motionweb.halal.ui.activity.scanner.dialog.ScannerResultDialog
import com.motionweb.halal.utils.*
import dagger.hilt.android.AndroidEntryPoint
import me.dm7.barcodescanner.zbar.Result
import me.dm7.barcodescanner.zbar.ZBarScannerView

@AndroidEntryPoint
class ScannerActivity : CoreActivity<ActivityScannerBinding>(), ZBarScannerView.ResultHandler {

    private val vm: ScannerVM by viewModels()

    private val camPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            when {
                granted -> {
                    initializeCamera()
                }
                !shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) -> {
                    showRationale()
                }
                else -> {
                    showRationale()
                }
            }
        }

    private var scannerView: ZBarScannerView? = null

    override fun getBinding(): ActivityScannerBinding =
        ActivityScannerBinding.inflate(layoutInflater)

    override fun setupViews() {
        super.setupViews()
        vb.ivBack.setOnClickListener {
            finish()
        }
        subscribeToLiveData()
    }

    private fun subscribeToLiveData() {
        observeBarcode()
    }

    private fun observeBarcode() {
        vm.barcode.observe(this, { result ->
            when (result) {
                is ResultWrapper.Empty -> {

                }
                is ResultWrapper.Error -> {
                    ScannerResultDialog.showDialog(
                        supportFragmentManager,
                        ScannerResultDialog.Type.NotFound,
                        getString(R.string.product_non_certified),
                        null
                    )
                    initializeCamera()
                    vb.flProgress.isVisible = false
                }
                ResultWrapper.Loading -> {
                    vb.flProgress.isVisible = true
                }
                is ResultWrapper.Success -> {
                    ScannerResultDialog.showDialog(
                        supportFragmentManager,
                        ScannerResultDialog.Type.Found,
                        getNormalStatusName(result.data.type),
                        when (getAppLang()) {
                            AppLang.KG -> result.data.titleKG
                            AppLang.RU -> result.data.titleRU
                        }
                    )
                    initializeCamera()
                    vb.flProgress.isVisible = false
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            initializeCamera()
            scannerView?.setResultHandler(this)
            scannerView?.startCamera()
        } else {
            camPermission.launch(Manifest.permission.CAMERA)
        }
    }

    override fun onPause() {
        super.onPause()
        scannerView?.stopCamera()
    }

    override fun onDestroy() {
        super.onDestroy()
        scannerView?.stopCamera()
    }

    private fun initializeCamera() {
        scannerView = ZBarScannerView(this)
        scannerView?.setResultHandler(this)
        scannerView?.setBackgroundColor(getColorExt(android.R.color.transparent))
        scannerView?.setBorderColor(getColorExt(R.color.main_blue))
        scannerView?.setLaserColor(getColorExt(R.color.main_blue))
        scannerView?.setBorderStrokeWidth(5)
        scannerView?.setSquareViewFinder(true)
        scannerView?.setupScanner()
        scannerView?.setAutoFocus(true)
        startQRCamera()
        vb.containerScanner.addView(scannerView)
    }

    private fun startQRCamera() {
        scannerView?.startCamera()
    }

    override fun handleResult(rawResult: Result?) {
        if (rawResult != null) {
            try {
                vm.findProductByBarcode(rawResult.contents.toLong())
            } catch (e: Exception) {
                e.printStackTrace()
                centeredToast(R.string.could_not_determine_the_code)
            }
        }
    }

    private fun showRationale() {
        vb.root.snackbarExt(
            R.string.permission_rationale_message,
            R.string.settings
        ) {
            startActivity(openSettingsActivityIntent())
        }
    }

    private fun getNormalStatusName(status: String): String {
        return when (status) {
            "good" -> getString(R.string.product_certified)
            "bad" -> getString(R.string.product_non_certified)
            "unknown" -> getString(R.string.unknown_error)
            else -> ""
        }
    }

    private fun getAppLang(): AppLang {
        val sharedPreferences = getSharedPreferences(PREF_DB_NAME, Context.MODE_PRIVATE)
        return when (sharedPreferences.get(PREF_TITLE_LANG, LANGUAGE_DEFAULT)) {
            "ru" -> AppLang.RU
            "ky" -> AppLang.KG
            else -> AppLang.RU
        }
    }

    companion object {
        fun start(context: Context?) {
            context?.startActivityExt<ScannerActivity>()
        }
    }


    enum class AppLang {
        KG, RU
    }

}