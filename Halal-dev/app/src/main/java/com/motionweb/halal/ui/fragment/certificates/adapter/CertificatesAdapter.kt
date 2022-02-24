package com.motionweb.halal.ui.fragment.certificates.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.motionweb.halal.data.network.certificates.Certificate
import com.motionweb.halal.databinding.ItemCertificateBinding
import com.motionweb.halal.utils.toImageUrl

class CertificatesAdapter(private val listener: OnCertificateListener) :
    RecyclerView.Adapter<CertificatesAdapter.CertificatesVH>() {

    private var itemList: List<Certificate> = emptyList()

    fun submitList(items: List<Certificate>) {
        this.itemList = items
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: CertificatesVH, position: Int) {
        holder.bind(itemList[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CertificatesVH =
        CertificatesVH.from(parent, listener)

    override fun getItemCount(): Int = itemList.size

    class CertificatesVH(
        private val vb: ItemCertificateBinding,
        private val listener: OnCertificateListener
    ) : RecyclerView.ViewHolder(vb.root) {

        fun bind(certificate: Certificate) {
            vb.root.setOnClickListener {
                listener(certificate)
            }
            with(vb) {
                ivLogo.load(certificate.logo.toImageUrl())
                tvTitle.text = certificate.title
            }
        }

        companion object {
            fun from(parent: ViewGroup, listener: OnCertificateListener): CertificatesVH {
                val inflater = LayoutInflater.from(parent.context)
                val vb = ItemCertificateBinding.inflate(inflater, parent, false)
                return CertificatesVH(vb, listener)
            }
        }

    }

    interface OnCertificateListener {
        operator fun invoke(certificate: Certificate)
    }

}