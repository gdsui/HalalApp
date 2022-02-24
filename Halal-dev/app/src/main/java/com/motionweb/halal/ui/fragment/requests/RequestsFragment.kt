package com.motionweb.halal.ui.fragment.requests

import androidx.fragment.app.viewModels
import com.motionweb.halal.core.ui.fragment.CoreFragment
import com.motionweb.halal.databinding.FragmentRequestsBinding
import com.motionweb.halal.repository.RequestModel
import com.motionweb.halal.utils.ResultWrapper
import com.motionweb.halal.utils.centeredToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RequestsFragment : CoreFragment<FragmentRequestsBinding>() {

    private val vm: RequestsVM by viewModels()

    override fun createVB(): FragmentRequestsBinding =
        FragmentRequestsBinding.inflate(layoutInflater)

    override fun setupViews() {
        vb.btnAdd.setOnClickListener {
            vm.addRequest(RequestModel(
                vb.riCompanyName.fetchText(),
                vb.riAddress.fetchText(),
                vb.riType.fetchText(),
                vb.riFullName.fetchText(),
                vb.riPhone.fetchText(),
                vb.riAddress.fetchText()
            ))
        }
        subscribeToLiveData()
    }

    private fun subscribeToLiveData() {
        vm.request.observe(viewLifecycleOwner) { result ->
            when (result) {
                is ResultWrapper.Empty -> {
                }
                is ResultWrapper.Error -> {
                    requireContext().centeredToast("Произошла ошибка!")
                }
                ResultWrapper.Loading -> {
                    requireContext().centeredToast("Ваша заявка обрабатывается.")
                }
                is ResultWrapper.Success -> {
                    requireContext().centeredToast("Ваша заявка принята!")
                    clear()
                }
            }
        }
    }

    private fun clear() {
        with(vb) {
            riAddress.setInputText("")
            riCompanyName.setInputText("")
            riFullName.setInputText("")
            riPhone.setInputText("")
            riType.setInputText("")
        }
    }

}