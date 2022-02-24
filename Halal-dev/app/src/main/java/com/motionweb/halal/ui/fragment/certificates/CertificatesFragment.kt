package com.motionweb.halal.ui.fragment.certificates

import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.motionweb.halal.R
import com.motionweb.halal.core.ui.fragment.CoreFragment
import com.motionweb.halal.data.network.certificates.Certificate
import com.motionweb.halal.databinding.FragmentCertificatesBinding
import com.motionweb.halal.ui.fragment.certificates.adapter.CertificatesAdapter
import com.motionweb.halal.ui.fragment.certificates.detail.DetailCertificateFragment
import com.motionweb.halal.utils.ResultWrapper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CertificatesFragment : CoreFragment<FragmentCertificatesBinding>(),
    CertificatesAdapter.OnCertificateListener {

    private val vm: CertificatesVM by viewModels()
    private val adapter: CertificatesAdapter = CertificatesAdapter(this)

    override fun createVB(): FragmentCertificatesBinding =
        FragmentCertificatesBinding.inflate(layoutInflater)

    override fun setupViews() {
        super.setupViews()
        setupRV()
        vm.fetchCertificates()
        subscribeToLiveData()
    }

    private fun setupRV() {
        vb.rvCertificates.adapter = adapter
    }

    private fun subscribeToLiveData() {
        observeCertificates()
    }

    private fun observeCertificates() {
        vm.certificates.observe(viewLifecycleOwner) { result ->
            when (result) {
                is ResultWrapper.Empty -> {
                }
                is ResultWrapper.Error -> {
                }
                ResultWrapper.Loading -> {
                }
                is ResultWrapper.Success -> {
                    adapter.submitList(result.data)
                }
            }
        }
    }

    /* Click listener for certificate item */
    override fun invoke(certificate: Certificate) {
        findNavController().navigate(R.id.detailCertificateFragment, bundleOf(
            DetailCertificateFragment.CERTIFICATE_KEY to certificate
        ))
    }

}