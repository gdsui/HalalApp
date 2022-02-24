package com.motionweb.halal.core.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class CoreVM : ViewModel() {

    open fun safeCall(action: suspend () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                action()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}