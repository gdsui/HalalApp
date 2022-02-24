package com.motionweb.halal.ui.activity.splash

import com.motionweb.halal.core.ui.viewmodel.CoreVM
import com.motionweb.halal.repository.SplashRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashVM @Inject constructor(
    private val repository: SplashRepository
) : CoreVM() {

    val isFirstLaunch: Boolean get() = repository.isFirstLaunch

}