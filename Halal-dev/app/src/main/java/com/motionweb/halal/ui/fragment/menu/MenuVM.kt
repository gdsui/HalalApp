package com.motionweb.halal.ui.fragment.menu

import com.motionweb.halal.core.ui.viewmodel.CoreVM
import com.motionweb.halal.repository.MenuRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MenuVM @Inject constructor(private val repository: MenuRepository) : CoreVM() {

    val username: String get() = repository.username
    val avatarPath: String get() = repository.avatarPath
    val isAuth: Boolean get () = repository.isAuth

}