package com.motionweb.halal.ui.fragment.ecode.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.motionweb.halal.data.network.ecode.ECode
import com.motionweb.halal.databinding.ItemEcodeBinding

class ECodeAdapter(private val listener: ECodeListener) :
    RecyclerView.Adapter<ECodeAdapter.ECodeViewHolder>() {

    private val differ = AsyncListDiffer(this, ECodeItemCallBack())

    fun submitItems(items: List<ECode>) {
        differ.submitList(items)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ECodeViewHolder {
        return ECodeViewHolder.from(parent, listener)
    }

    override fun onBindViewHolder(holder: ECodeViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int = differ.currentList.size

    class ECodeViewHolder(
        private val binding: ItemEcodeBinding,
        private val listener: ECodeListener
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(eCode: ECode) {
            binding.tvName.text = eCode.code
            binding.tvDescription.text = eCode.title
            eCode.toFoodType()?.let { binding.foodType.setFoodType(it) }
            binding.root.setOnClickListener {
                listener.onEcodeClick(eCode)
            }
        }

        companion object {
            fun from(parent: ViewGroup, listener: ECodeListener): ECodeViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ItemEcodeBinding.inflate(inflater, parent, false)
                return ECodeViewHolder(binding, listener)
            }
        }
    }

    interface ECodeListener {
        fun onEcodeClick(eCode: ECode)
    }

}

class ECodeItemCallBack : DiffUtil.ItemCallback<ECode>() {
    override fun areItemsTheSame(oldItem: ECode, newItem: ECode): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ECode, newItem: ECode): Boolean {
        return oldItem == newItem
    }
}