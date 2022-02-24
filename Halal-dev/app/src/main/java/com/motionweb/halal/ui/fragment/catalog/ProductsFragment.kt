package com.motionweb.halal.ui.fragment.catalog

import android.content.Context
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.motionweb.halal.R
import com.motionweb.halal.core.ui.fragment.CoreFragment
import com.motionweb.halal.data.network.catalog.models.Product
import com.motionweb.halal.databinding.FragmentCatalogBinding
import com.motionweb.halal.ui.activity.scanner.ScannerActivity
import com.motionweb.halal.ui.fragment.catalog.adapter.CatalogAdapter
import com.motionweb.halal.ui.fragment.catalog.adapter.ItemClickListener
import com.motionweb.halal.utils.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductsFragment : CoreFragment<FragmentCatalogBinding>(), ItemClickListener {

    private val vm: ProductsVM by viewModels()
    private val args: ProductsFragmentArgs by navArgs()
    private val productAdapter: CatalogAdapter by lazy(LazyThreadSafetyMode.NONE) {
        CatalogAdapter(this, getAppLang())
    }
    private var allProducts = listOf<Product>()
    private val filteredList = MutableLiveData<List<Product>>()

    override fun createVB() = FragmentCatalogBinding.inflate(layoutInflater)

    override fun setupViews() {
        super.setupViews()
        setupSearch()
        vb.rv.adapter = productAdapter
        vb.refresher.setOnRefreshListener { vm.fetchCompanyProducts(args.companyId.toInt()) }
        vm.fetchCompanyProducts(args.companyId.toInt())
        subscribeToLiveData()
        setupPrayer()
    }

    private fun subscribeToLiveData() {
        vm.allProducts.observe(viewLifecycleOwner, { result ->
            when (result) {
                is ResultWrapper.Empty -> {
                    vb.refresher.isRefreshing = false
                    showEmptyList(true)
                }
                is ResultWrapper.Error -> {
                    vb.refresher.isRefreshing = false
                    showEmptyList(true)
                }
                ResultWrapper.Loading -> {
                    vb.refresher.isRefreshing = true
                }
                is ResultWrapper.Success -> {
                    vb.refresher.isRefreshing = false
                    allProducts = result.data
                    vb.etSearch.setText("")
                }
            }
        })
        filteredList.observe(viewLifecycleOwner, {
            showEmptyList(it.isEmpty())
            productAdapter.submitList(it)
        })
    }

    private fun setupSearch() {
        vb.etSearch.doAfterTextChanged { text ->
            filteredList.value = allProducts.filter { it.titleRu.contains(text.toString(), true) }
        }
    }

    private fun showEmptyList(value: Boolean) {
        vb.emptyLabel.isVisible = value
        vb.rv.isVisible = !value
    }

    override fun onItemClick(id: Long) {
        android.util.Log.e("TAG", "onProductClick id - $id")
    }

    private fun getAppLang(): ScannerActivity.AppLang {
        val sharedPreferences =
            parentActivity.getSharedPreferences(PREF_DB_NAME, Context.MODE_PRIVATE)
        return when (sharedPreferences.get(PREF_TITLE_LANG, LANGUAGE_DEFAULT)) {
            "ru" -> ScannerActivity.AppLang.RU
            "ky" -> ScannerActivity.AppLang.KG
            else -> ScannerActivity.AppLang.RU
        }
    }

    private fun setupPrayer() {
        vb.prayerView.setBottomText(getCurrentDate().toString())
        vb.prayerView.setPrayerTopText(vm.nearestPrayer)
        vb.prayerView.setPrayerBottomText(vm.prayerTime)
        vb.prayerView.setCalendarClickListener {
            findNavController().navigate(R.id.islamicCalendarFragment)
        }
        vb.prayerView.setPrayerClickListener {
            findNavController().navigate(R.id.prayerTimeFragment)
        }
    }
}