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
import com.motionweb.halal.databinding.FragmentEditUsernameBinding
import com.motionweb.halal.utils.Keys

class EditUsernameFragment : CoreFragment<FragmentEditUsernameBinding>() {

    private var currentName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        currentName = arguments?.getString(Keys.USERNAME)
    }

    override fun onStart() {
        super.onStart()
        vb.etEdit.doAfterTextChanged {
            if (it.toString().trim().isNotEmpty()) {
                currentName = it.toString()
            }
        }
    }

    override fun setupViews() {
        super.setupViews()
        vb.etEdit.setText(currentName ?: "")
        vb.etEdit.requestFocus()
        (requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
            .showSoftInput(vb.etEdit, InputMethodManager.SHOW_IMPLICIT)
    }

    override fun createVB(): FragmentEditUsernameBinding =
        FragmentEditUsernameBinding.inflate(layoutInflater)

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.edit_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.done -> {
                currentName?.let {
                    setFragmentResult(Keys.USERNAME_REQUEST, bundleOf(Keys.USERNAME to it))
                }
                findNavController().popBackStack()
            }
        }
        return super.onOptionsItemSelected(item)
    }

}