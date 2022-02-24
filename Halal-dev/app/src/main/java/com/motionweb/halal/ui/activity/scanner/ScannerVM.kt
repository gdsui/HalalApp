package com.motionweb.halal.ui.activity.scanner

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.motionweb.halal.core.ui.viewmodel.CoreVM
import com.motionweb.halal.data.network.barcode_scanner.Barcode
import com.motionweb.halal.repository.BarcodeScannerRepository
import com.motionweb.halal.utils.ResultWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScannerVM @Inject constructor(private val repository: BarcodeScannerRepository) : CoreVM() {

    private val _barcode: MutableLiveData<ResultWrapper<Barcode>> = MutableLiveData()
    val barcode: LiveData<ResultWrapper<Barcode>> get() = _barcode

    fun findProductByBarcode(code: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            _barcode.postValue(ResultWrapper.loading())
            try {
                val response = repository.findProductByBarcode(code)
                _barcode.postValue(ResultWrapper.success(response))
            } catch (e: Exception) {
                _barcode.postValue(ResultWrapper.error(e))
            }
        }
    }

}