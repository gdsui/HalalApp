package com.motionweb.halal.ui.fragment.certificates.detail

import android.os.Bundle
import coil.load
import com.motionweb.halal.core.ui.fragment.CoreFragment
import com.motionweb.halal.data.network.certificates.Certificate
import com.motionweb.halal.databinding.FragmentDetailCertificateBinding
import com.motionweb.halal.utils.toImageUrl

class DetailCertificateFragment : CoreFragment<FragmentDetailCertificateBinding>() {

    private var certificate: Certificate? = null

    override fun createVB(): FragmentDetailCertificateBinding =
        FragmentDetailCertificateBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        certificate = arguments?.getParcelable(CERTIFICATE_KEY)
    }

    override fun setupViews() {
        super.setupViews()
        with(vb) {
            certificate?.let {
                ivLogo.load(it.logo.toImageUrl())
                ivOne.load("http://159.65.120.217/" + it.certificates.imageOne)
                ivTwo.load("http://159.65.120.217/" + it.certificates.imageSecond)
                tvDescription.text = it.description
                tvTitle.text = it.title
            }
        }
    }

    companion object {
        const val CERTIFICATE_KEY: String = "CERTIFICATE_KEY"
    }

}