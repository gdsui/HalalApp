package com.motionweb.halal.ui.fragment.profile.edit

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import androidx.core.os.bundleOf
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.motionweb.halal.R
import com.motionweb.halal.core.ui.fragment.CoreFragment
import com.motionweb.halal.databinding.FragmentEditPhoneBinding
import com.motionweb.halal.utils.Keys

class EditPhoneFragment : CoreFragment<FragmentEditPhoneBinding>() {

    private var currentPhone: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        currentPhone = arguments?.getString(Keys.PHONE)
    }

    override fun onStart() {
        super.onStart()
        vb.etEdit.doAfterTextChanged {
            if (it.toString().trim().isNotEmpty()) {
                currentPhone = it.toString()
            }
        }
    }

    override fun setupViews() {
        super.setupViews()
        vb.etEdit.setText(currentPhone ?: "")
        vb.etEdit.requestFocus()
        (requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
            .showSoftInput(vb.etEdit, InputMethodManager.SHOW_IMPLICIT)
    }

    override fun createVB(): FragmentEditPhoneBinding =
        FragmentEditPhoneBinding.inflate(layoutInflater)

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.edit_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.done -> {
                currentPhone?.let {
                    setFragmentResult(Keys.PHONE_REQUEST, bundleOf(Keys.PHONE to it))
                }
                findNavController().popBackStack()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}