package com.motionweb.halal.ui.fragment.catalog.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.motionweb.halal.R
import com.motionweb.halal.data.network.catalog.models.Category
import com.motionweb.halal.data.network.catalog.models.Company
import com.motionweb.halal.data.network.catalog.models.CompanyDetail
import com.motionweb.halal.data.network.catalog.models.Product
import com.motionweb.halal.databinding.ItemCatalogBinding
import com.motionweb.halal.ui.activity.scanner.ScannerActivity
import com.motionweb.halal.utils.toImageUrl
import kotlin.math.log

class CatalogAdapter(
    private val listener: ItemClickListener,
    private val appLang: ScannerActivity.AppLang? = null
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val differ = AsyncListDiffer(this, CatalogItemCallback())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CatalogVH.create(parent, listener, appLang)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = differ.currentList[position]) {
            is Category -> (holder as CatalogVH).bind(item)
            is Product -> (holder as CatalogVH).bind(item)
            is Company -> (holder as CatalogVH).bind(item)
        }
    }

    override fun getItemCount() = differ.currentList.size

    fun submitList(list: List<Any>) {
        differ.submitList(list)
    }
}

class CatalogItemCallback : DiffUtil.ItemCallback<Any>() {
    override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
        if (oldItem::class != newItem::class) return false
        return when (oldItem) {
            is Category -> oldItem.id == (newItem as Category).id
            is Company -> oldItem.id == (newItem as Company).id
            is Product -> oldItem.id == (newItem as Product).id
            is CompanyDetail -> oldItem.id == (newItem as CompanyDetail).id
            else -> throw IllegalArgumentException("Unknown class type: $oldItem")
        }
    }

    override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {
        if (oldItem::class != newItem::class) return false
        return when (oldItem) {
            is Category -> (newItem as Category) == oldItem
            is Company -> (newItem as Company) == oldItem
            is Product -> (newItem as Product) == oldItem
            is CompanyDetail -> (newItem as CompanyDetail) == oldItem
            else -> throw IllegalArgumentException("Unknown class type: $oldItem")
        }
    }
}


interface ItemClickListener {
    fun onItemClick(id: Long) {}
    fun onFavouriteClick(id: Long, isFavourite: Boolean) {}
}

class CatalogVH(
    private val view: ItemCatalogBinding,
    private val listener: ItemClickListener,
    private val appLang: ScannerActivity.AppLang?
) : RecyclerView.ViewHolder(view.root) {

    fun bind(data: Category) {
        with(view) {
            view.cvRoot.setOnClickListener { listener.onItemClick(data.id.toLong()) }
            ivChevron.isVisible = true
            cbFavourite.isVisible = false
            ivIcon.isVisible = false
            flCategory.isVisible = true
            val logo =
                if (data.logo?.startsWith("/media") == true) "http://159.65.120.217" + data.logo else data.logo?.toImageUrl()
            ivCategory.load(logo)
            tvTitle.text =
                if (appLang == ScannerActivity.AppLang.KG) data.nameKg else data.nameRu
        }
    }

    fun bind(data: Product) {
        with(view) {
            view.cvRoot.setOnClickListener { listener.onItemClick(data.id.toLong()) }
            ivChevron.isVisible = false
            cbFavourite.isVisible = true
            ivIcon.isVisible = true
            flCategory.isVisible = false
            val logo =
                if (data.photo?.startsWith("/media") == true) "http://159.65.120.217" + data.photo else data.photo?.toImageUrl()
            loadImage(logo)
            tvTitle.text = if (appLang == ScannerActivity.AppLang.KG) data.titleKg else data.titleRu
            cbFavourite.isVisible = false
        }
    }

    fun bind(data: Company) {
        with(view) {
            view.cvRoot.setOnClickListener { listener.onItemClick(data.id.toLong()) }
            ivChevron.isVisible = false
            cbFavourite.isVisible = true
            ivIcon.isVisible = true
            flCategory.isVisible = false
            if (data.logo != null) {
                val rightIconUrl = "http://159.65.120.217/" + data.logo
                loadImage(rightIconUrl)
            }
            tvTitle.text = data.name
            cbFavourite.isChecked = data.isFavorite
            cbFavourite.setOnCheckedChangeListener { _, isChecked ->
                listener.onFavouriteClick(
                    data.id.toLong(),
                    isChecked
                )
            }
        }
    }

    private fun loadImage(url: String?) {
        when (!url.isNullOrEmpty()) {
            true -> {
                view.ivIcon.load(url) { placeholder(R.drawable.ic_favourite_filled) }
            } //todo: change placeholder icon
            else -> view.ivIcon.load(R.drawable.ic_favourite_filled) //todo: change default icon
        }
    }

    companion object {
        fun create(
            parent: ViewGroup,
            listener: ItemClickListener,
            appLang: ScannerActivity.AppLang?,
        ): CatalogVH {
            return CatalogVH(
                ItemCatalogBinding
                    .inflate(LayoutInflater.from(parent.context), parent, false), listener, appLang
            )
        }
    }
}