package com.motionweb.halal.ui.fragment.catalog

import android.content.Context
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.motionweb.halal.R
import com.motionweb.halal.core.ui.fragment.CoreFragment
import com.motionweb.halal.databinding.FragmentCompanyDetailBinding
import com.motionweb.halal.ui.activity.scanner.ScannerActivity
import com.motionweb.halal.ui.fragment.catalog.adapter.CatalogAdapter
import com.motionweb.halal.ui.fragment.catalog.adapter.ItemClickListener
import com.motionweb.halal.utils.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CompanyDetailFragment : CoreFragment<FragmentCompanyDetailBinding>(), ItemClickListener {

    private val vm: CompanyDetailVM by viewModels()
    private val args: CompanyDetailFragmentArgs by navArgs()

    private val categoriesAdapter: CatalogAdapter by lazy(LazyThreadSafetyMode.NONE) {
        CatalogAdapter(this, getAppLang())
    }

    override fun createVB() = FragmentCompanyDetailBinding.inflate(layoutInflater)

    override fun setupViews() {
        super.setupViews()
        vb.rv.adapter = categoriesAdapter
        vb.refresher.setOnRefreshListener { vm.fetchCompanyDetail(args.companyId.toInt()) }
        vm.fetchCompanyDetail(args.companyId.toInt())
        subscribeToLiveData()
        setupPrayer()
    }

    private fun subscribeToLiveData() {
        vm.company.observe(viewLifecycleOwner, { result ->
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
                    vb.tvCompanyName.text = result.data.name
                    vb.ivCompanyIcon.load("http://159.65.120.217${result.data.logo}")
                    vb.refresher.isRefreshing = false
                    showEmptyList(false)
                    categoriesAdapter.submitList(listOf(result.data.category))
                }
            }
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
        findNavController().navigate(
            CompanyDetailFragmentDirections.toProductsFragment(
                args.companyId,
                id
            )
        )
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