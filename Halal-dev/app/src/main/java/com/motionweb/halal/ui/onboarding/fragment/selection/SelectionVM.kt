package com.motionweb.halal.ui.onboarding.fragment.selection

import com.motionweb.halal.core.ui.viewmodel.CoreVM
import com.motionweb.halal.repository.SelectionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SelectionVM @Inject constructor(private val repository: SelectionRepository) : CoreVM() {

    fun setLaunch(isLaunch: Boolean) {
        repository.setLaunch(isLaunch)
    }

}