package com.motionweb.halal.core.ui.activity

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.motionweb.halal.utils.LocaleService

abstract class CoreActivity<VB : ViewBinding> : AppCompatActivity() {

    protected val vb: VB by lazy(LazyThreadSafetyMode.NONE) { getBinding() }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(
            LocaleService.updateBaseContextLocale(newBase)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(vb.root)
        setupViews()
    }

    open fun setupViews() {}

    abstract fun getBinding(): VB

}