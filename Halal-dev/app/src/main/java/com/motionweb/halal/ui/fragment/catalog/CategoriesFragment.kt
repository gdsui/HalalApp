package com.motionweb.halal.ui.fragment.catalog

import android.content.Context
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import com.motionweb.halal.R
import com.motionweb.halal.core.ui.fragment.CoreFragment
import com.motionweb.halal.data.network.catalog.models.Category
import com.motionweb.halal.databinding.FragmentCatalogBinding
import com.motionweb.halal.ui.activity.scanner.ScannerActivity
import com.motionweb.halal.ui.fragment.catalog.adapter.CatalogAdapter
import com.motionweb.halal.ui.fragment.catalog.adapter.ItemClickListener
import com.motionweb.halal.utils.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoriesFragment : CoreFragment<FragmentCatalogBinding>(), ItemClickListener {

    private val vm: CategoryVM by viewModels()
    private val categoriesAdapter: CatalogAdapter by lazy(LazyThreadSafetyMode.NONE) {
        CatalogAdapter(this, getAppLang())
    }

    private var allCategories = listOf<Category>()
    private val filteredList = MutableLiveData<List<Category>>()

    override fun createVB() = FragmentCatalogBinding.inflate(layoutInflater)

    override fun setupViews() {
        super.setupViews()
        setupSearch()
        vb.rv.adapter = categoriesAdapter
        vb.refresher.setOnRefreshListener { vm.fetchAllCategories() }
        vm.fetchAllCategories()
        subscribeToLiveData()
        setupPrayer()
    }

    private fun setupSearch() {
        vb.etSearch.doAfterTextChanged { text ->
            filteredList.value = allCategories.filter { it.nameRu.contains(text.toString(), true) }
        }
    }

    private fun subscribeToLiveData() {
        vm.allCategories.observe(viewLifecycleOwner, { result ->
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
                    allCategories = result.data
                    vb.etSearch.setText("")
                }
            }
        })
        filteredList.observe(viewLifecycleOwner, {
            showEmptyList(it.isEmpty())
            categoriesAdapter.submitList(it)
        })
    }

    private fun showEmptyList(value: Boolean) {
        vb.emptyLabel.isVisible = value
        vb.rv.isVisible = !value
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

    override fun onItemClick(id: Long) {
        findNavController().navigate(CategoriesFragmentDirections.toCompaniesFragment(id))
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
