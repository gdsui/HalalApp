package com.motionweb.halal.ui.fragment.questions

import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.motionweb.halal.R
import com.motionweb.halal.core.ui.fragment.CoreFragment
import com.motionweb.halal.databinding.FragmentQuestionAnswerBinding
import com.motionweb.halal.utils.ResultWrapper
import com.motionweb.halal.utils.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuestionAnswerFragment : CoreFragment<FragmentQuestionAnswerBinding>() {

    override fun createVB() = FragmentQuestionAnswerBinding.inflate(layoutInflater)

    private val vm: QuestionAnswerVM by viewModels()
    private val questionAdapter = QuestionsAdapter()

    override fun onStart() {
        super.onStart()
        vb.etEdit.doOnTextChanged { _, _, _, _ ->
            vb.etInputQuestion.error = null
        }
    }

    override fun setupViews() {
        super.setupViews()
        subscribeToLiveData()
        initAdapter()
        vm.fetchAllQuestions()
        setupListeners()
    }

    private fun setupListeners() {
        vb.btnSave.setOnClickListener {
            if (vb.etEdit.text?.trim().isNullOrEmpty()) {
                vb.etInputQuestion.error = getString(R.string.edit_text_error_message)
                return@setOnClickListener
            }
            if (vm.isAuthorized) {
                vm.createQuestion(vb.etEdit.text.toString())
            } else {
                Snackbar.make(vb.root, getString(R.string.not_registered), Snackbar.LENGTH_LONG)
                    .setAction(R.string.register) {
                        findNavController().navigate(R.id.profileFragment)
                    }
                    .show()
            }
        }
        vb.btnCancel.setOnClickListener {
            vb.etEdit.setText("")
        }
    }

    private fun subscribeToLiveData() {
        vm.questions.observe(viewLifecycleOwner) { result ->
            when (result) {
                is ResultWrapper.Success -> questionAdapter.dataSource = result.data
                is ResultWrapper.Empty -> {}
                is ResultWrapper.Error -> {}
                is ResultWrapper.Loading -> {}
            }

        }
        vm.questionIsSent.observe(viewLifecycleOwner, {
            if (it == true) {
                requireContext().toast(R.string.question_is_sent)
                vm.fetchAllQuestions()
            }
        })
    }

    private fun initAdapter() {
        vb.rv.adapter = questionAdapter
    }

}