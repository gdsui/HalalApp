package com.motionweb.halal.ui.fragment.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.motionweb.halal.core.ui.viewmodel.CoreVM
import com.motionweb.halal.data.network.profile.UserModel
import com.motionweb.halal.repository.ProfileRepository
import com.motionweb.halal.utils.Gender
import com.motionweb.halal.utils.ResultWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject
import kotlin.random.Random

private typealias Register = RegisterModel

@HiltViewModel
class ProfileVM @Inject constructor(private val repository: ProfileRepository) : CoreVM() {

    init {
        fetchProfile()
    }

    private val _username: MutableLiveData<String> = MutableLiveData(repository.username)
    val username get() = _username

    private val _userPhone: MutableLiveData<String> = MutableLiveData(repository.phone)
    val userPhone get() = _userPhone

    private val _gender: MutableLiveData<String> = MutableLiveData(repository.gender)
    val gender get() = _gender

    private val _profile: MutableLiveData<ResultWrapper<UserModel>> = MutableLiveData()
    val profile: LiveData<ResultWrapper<UserModel>> get() = _profile

    private val _avatar: MutableLiveData<AvatarState> = MutableLiveData()
    val avatar: LiveData<AvatarState> get() = _avatar

    val avatarPath: String get() = repository.avatarPath

    private val _email: MutableLiveData<String> = MutableLiveData(repository.email)
    val email: LiveData<String> get() = _email

    private val _isAuth: MutableLiveData<Boolean> = MutableLiveData(repository.isAuth)
    val isAuth: LiveData<Boolean> get() = _isAuth

    fun setGender(gender: Gender) {
        _gender.value = gender.gender
    }

    fun setUserAvatar(state: AvatarState) {
        _avatar.postValue(state)
    }

    fun register(register: Register) {
        viewModelScope.launch(Dispatchers.IO) {
            _profile.postValue(ResultWrapper.loading())
            try {
                val response = repository.register(
                    createAvatar(getAvatar(avatar.value)),
                    register.username,
                    register.email,
                    register.phone,
                    gender.value ?: "",
                    register.password,
                    register.favoriteCompanies
                )
                _profile.postValue(ResultWrapper.success(response))
            } catch (e: Exception) {
                _profile.postValue(ResultWrapper.error(e))
                e.printStackTrace()
            }
        }
    }

    private fun saveProfileData(profile: UserModel) {
        with(repository) {
            viewModelScope.launch {
                setIsAuthorized(true)
            }
            setUsername(profile.username)
            setUserEmail(profile.email)
            setPhone(profile.phone)
            when (profile.gender.replace("\"", "")) {
                Gender.FEMALE.gender -> {
                    setGender(Gender.FEMALE)
                }
                Gender.MALE.gender -> {
                    setGender(Gender.MALE)
                }
            }
        }
        setAvatarFromServer(profile.avatar)
    }

    private fun getAvatar(state: AvatarState?): String? =
        when (state) {
            is AvatarState.Default -> state.path
            is AvatarState.FromGallery -> state.path
            is AvatarState.FromServer -> state.url
            else -> null
        }

    private fun setAvatarFromServer(url: String) {
        if (url.isNotEmpty()) {
            setUserAvatar(AvatarState.FromServer(url))
            repository.setAvatarPath(url)
        }
    }

    private fun createAvatar(path: String?): MultipartBody.Part? {
        if (path != null) {
            val avatar = File(path)
            val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), avatar)
            return MultipartBody.Part.createFormData(
                "avatar",
                "${Random.nextInt()}${avatar.name}",
                requestFile
            )
        }
        return null
    }

    fun exit() {
        viewModelScope.launch {
            repository.setIsAuthorized(false)
        }
    }

    private fun updateUserData(
        username: String,
        email: String,
        avatarPath: String,
        phone: String,
        gender: String
    ) {
        _username.value = username
        _email.value = email
        _avatar.value = AvatarState.FromServer(avatarPath)
        _userPhone.value = phone
        _gender.value = gender.replace("\"", "")
    }

    private fun fetchProfile() {
        viewModelScope.launch {
            try {
                val profile = repository.fetchProfile()
                if (profile != null) {
                    saveProfileData(
                        UserModel(
                            profile.id,
                            profile.username,
                            profile.email,
                            profile.avatar,
                            profile.phone,
                            profile.gender,
                            null,
                            null,
                            null
                        )
                    )
                    updateUserData(
                        profile.username,
                        profile.email,
                        profile.avatar,
                        profile.phone,
                        profile.gender
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}

data class RegisterModel(
    val username: String,
    val phone: String,
    val email: String,
    val password: String,
    val avatarPath: String,
    val favoriteCompanies: List<Int>?
)