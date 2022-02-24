package com.motionweb.halal.ui.fragment.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.motionweb.halal.core.ui.viewmodel.CoreVM
import com.motionweb.halal.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginVM @Inject constructor(private val repository: ProfileRepository) : CoreVM() {

    val isLogin: MutableLiveData<Boolean> = MutableLiveData()

    fun login(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.login(
                    email,
                    password
                )
                repository.setIsAuthorized(response)
                isLogin.postValue(response)
            } catch (e: Exception) {
                isLogin.postValue(false)
                e.printStackTrace()
            }
        }
    }

}