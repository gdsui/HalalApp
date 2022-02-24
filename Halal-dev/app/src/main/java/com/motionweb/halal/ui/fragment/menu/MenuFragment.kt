package com.motionweb.halal.ui.fragment.menu

import android.content.Intent
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import coil.load
import com.motionweb.halal.R
import com.motionweb.halal.core.ui.fragment.CoreFragment
import com.motionweb.halal.databinding.FragmentMenuBinding
import com.motionweb.halal.utils.toImageUrl
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class MenuFragment : CoreFragment<FragmentMenuBinding>() {

    private val vm: MenuVM by viewModels()

    override fun createVB(): FragmentMenuBinding =
        FragmentMenuBinding.inflate(layoutInflater)

    override fun onStart() {
        super.onStart()
        setupRowListeners()
        if (vm.isAuth) {
            vb.ivAvatar.load("http://159.65.120.217" + vm.avatarPath)
        } else {
            vb.ivAvatar.load(File(vm.avatarPath))
        }
        vb.tvUsername.text = vm.username
    }

    private fun setupRowListeners() {
        vb.nrvLk.setOnClickListener {
            if (vm.isAuth) {
                findNavController().navigate(MenuFragmentDirections.toProfileFragment())
            } else {
                findNavController().navigate(R.id.loginFragment)
            }
        }
        vb.nrvEvents.setOnClickListener {
            findNavController().navigate(R.id.eventFragment)
        }
        vb.nrvQa.setOnClickListener {
            findNavController().navigate(MenuFragmentDirections.toQuestionsFragment())
        }
        vb.nrvAboutHalal.setOnClickListener {
            findNavController().navigate(R.id.certificatesFragment)
        }
        vb.nrvAboutApp.setOnClickListener {
            findNavController().navigate(R.id.aboutFragment)
        }
        vb.nrvShare.setOnClickListener {
            openShareActivity()
        }
        vb.nrvSettings.setOnClickListener {
            findNavController().navigate(R.id.settingsFragment)
        }
    }

    private fun openShareActivity() {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "Тестовая ссылка..")
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

    companion object {
        fun newInstance(): MenuFragment = MenuFragment()
    }
}