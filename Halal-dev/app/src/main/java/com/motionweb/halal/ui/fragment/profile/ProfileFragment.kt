package com.motionweb.halal.ui.fragment.profile

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import coil.load
import com.motionweb.halal.R
import com.motionweb.halal.core.ui.fragment.CoreFragment
import com.motionweb.halal.databinding.FragmentProfileBinding
import com.motionweb.halal.utils.*
import com.motionweb.halal.utils.profile.ProfileDialogTypes
import dagger.hilt.android.AndroidEntryPoint
import java.io.File


@AndroidEntryPoint
class ProfileFragment : CoreFragment<FragmentProfileBinding>() {

    private val vm: ProfileVM by viewModels()

    private var imageUri: Uri? = null

    private val getContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            imageUri = uri
            vm.setUserAvatar(AvatarState.FromGallery(parentActivity.getPath(uri)))
        }

    private val openSettings =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {}

    private val readExternalStoragePermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            when {
                granted -> {
                    // доступ к чтению разрешен
                    openStorage()
                }
                else -> {
                    // доступ к чтению запрещен, пользователь отклонил запрос
                    showSnackbarWithSettings()
                }
            }
        }

    override fun createVB() = FragmentProfileBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun setupViews() {
        setupListeners()
        subscribeToLiveData()
        setupAvatar()
    }

    private fun showSnackbarWithSettings() {
        vb.root.snackbarExt(R.string.access_permission, R.string.settings) {
            openSettings.launch(requireContext().openSettingsActivityIntent())
        }
    }

    private fun setupAvatar() {
        val avatarState: AvatarState =
            when {
                vm.isAuth.value == true -> {
                    AvatarState.FromServer(vm.avatarPath)
                }
                imageUri != null -> AvatarState.FromGallery(parentActivity.getPath(imageUri))
                else -> {
                    AvatarState.Default(requireContext().getAssetsFilePath("profile.png"))
                }
            }
        vm.setUserAvatar(avatarState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        if (vm.isAuth.value == true) {
            inflater.inflate(R.menu.authorized_menu, menu)
        } else super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.exit -> {
                vm.exit()
                findNavController().navigate(
                    R.id.loginFragment,
                    null,
                    NavOptions.Builder().setPopUpTo(R.id.menuFragment, true).build()
                )
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onStart() {
        super.onStart()
        setupInputs()
    }

    private fun setupListeners() {
        vb.ivCamera.setOnClickListener {
            openStorage()
        }
        vb.btnSave.setOnClickListener {
            if (vm.isAuth.value == true) {
                requireContext().centeredToast(R.string.already_logged_in)
                return@setOnClickListener
            }
            if (checkUsername() && checkPhone() && checkEmailInput()) {
                if (checkPasswordInput() && checkSimilarPasswords() && checkGender()) {
                    register()
                }
            }
        }
    }

    private fun register() {
        val avatar = getAvatar()
        if (avatar != null) {
            vm.register(
                RegisterModel(
                    vb.etUsername.text.toString(),
                    vb.etPhone.text.toString(),
                    vb.etEmail.text.toString(),
                    vb.etPassword.text.toString(),
                    avatar,
                    null
                )
            )
        }
    }

    private fun getAvatar(): String? {
        if (imageUri != null) {
            return parentActivity.getPath(imageUri)
        }
        return requireContext().getAssetsFilePath(Keys.AVATAR_NAME)
    }

    private fun checkEmailInput(): Boolean {
        if (vb.etEmail.text?.trim().isNullOrEmpty()) {
            requireContext().centeredToast(R.string.email_input_error)
            return false
        }
        return true
    }

    private fun checkPasswordInput(): Boolean {
        val password = vb.etPassword.text?.trim()
        if (password.isNullOrEmpty()) {
            requireContext().centeredToast(R.string.password_input_error)
            return false
        }
        return true
    }

    private fun checkSimilarPasswords(): Boolean {
        val password = vb.etPassword.text?.trim()
        val repeatPassword = vb.etRepeatPassword.text?.trim()
        if (password.toString().similarityCheck(repeatPassword.toString())) {
            return true
        }
        requireContext().centeredToast(R.string.passwords_dont_match)
        return false
    }

    private fun checkUsername(): Boolean {
        if (vb.etUsername.text?.trim().isNullOrEmpty()) {
            vb.etUsername.requestFocus()
            requireContext().centeredToast(R.string.username_input_error)
            return false
        }
        return true
    }

    private fun checkPhone(): Boolean {
        if (vb.etPhone.text?.trim().isNullOrEmpty()) {
            vb.etPhone.requestFocus()
            requireContext().centeredToast(R.string.phone_input_error)
            return false
        }
        return true
    }

    private fun checkGender(): Boolean {
        return if (vb.rbFemale.isChecked || vb.rbMale.isChecked) true
        else {
            requireContext().centeredToast(R.string.select_gender_error)
            false
        }
    }

    private fun setupInputs() {
        vb.rbFemale.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                vm.setGender(Gender.FEMALE)
            }
        }
        vb.rbMale.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                vm.setGender(Gender.MALE)
            }
        }
    }

    private fun subscribeToLiveData() {
        vm.username.observe(viewLifecycleOwner, {
            vb.tvHeadUsername.text = it
            vb.etUsername.setText(it)
        })
        vm.userPhone.observe(viewLifecycleOwner, {
            vb.etPhone.setText(it)
        })
        vm.email.observe(viewLifecycleOwner, {
            vb.etEmail.setText(it)
        })
        subscribeAvatar()
        subscribeGender()
        subscribeToProfile()
    }

    private fun subscribeToProfile() {
        vm.profile.observe(viewLifecycleOwner, { result ->
            when (result) {
                is ResultWrapper.Empty -> return@observe
                is ResultWrapper.Error -> {
                    vb.flProgress.isVisible = false
                    if (result.code != null && result.code == 400) {
                        showUserExistDialog()
                    }
                }
                ResultWrapper.Loading -> vb.flProgress.isVisible = true
                is ResultWrapper.Success -> {
                    clearPasswordInputs()
                    vb.flProgress.isVisible = false
                    showSuccessRegistrationDialog()
                    findNavController().navigate(
                        R.id.loginFragment,
                        null,
                        NavOptions.Builder().setPopUpTo(R.id.loginFragment, true).build()
                    )
                }
            }
        })
    }

    private fun clearPasswordInputs() = with(vb) {
        etPassword.setText("")
        etRepeatPassword.setText("")
    }

    private fun showUserExistDialog() = ProfileDialogTypes.getDialog(
        parentFragmentManager,
        ProfileDialogTypes.Types.USER_EXIST
    )

    private fun showSuccessRegistrationDialog() = ProfileDialogTypes.getDialog(
        parentFragmentManager,
        ProfileDialogTypes.Types.SUCCESSFUL_REGISTRATION
    )

    private fun subscribeAvatar() {
        vm.avatar.observe(viewLifecycleOwner, { avatar ->
            when (avatar) {
                is AvatarState.FromServer -> {
                    vb.ivAvatar.load("http://159.65.120.217" + avatar.url)
                }
                is AvatarState.Default -> {
                    if (avatar.path != null) {
                        vb.ivAvatar.load(File(avatar.path))
                    }
                }
                is AvatarState.FromGallery -> {
                    if (avatar.path != null) {
                        vb.ivAvatar.load(File(avatar.path))
                    }
                }
            }
        })
    }

    private fun subscribeGender() {
        vm.gender.observe(viewLifecycleOwner, { gender ->
            when (gender) {
                Gender.MALE.gender -> vb.rbMale.isChecked = true
                Gender.FEMALE.gender -> vb.rbFemale.isChecked = true
            }
        })
    }

    private fun requestStoragePermission() {
        val permission = Manifest.permission.READ_EXTERNAL_STORAGE
        if (shouldShowRequestPermissionRationale(permission)) {
            readExternalStoragePermission.launch(permission)
        } else {
            readExternalStoragePermission.launch(permission)
        }
    }

    private fun openStorage() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            getContent.launch("image/*")
        } else {
            requestStoragePermission()
        }
    }

}