package com.motionweb.halal.ui.fragment.catalog

import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.motionweb.halal.R
import com.motionweb.halal.core.ui.fragment.CoreFragment
import com.motionweb.halal.data.network.catalog.models.Company
import com.motionweb.halal.databinding.FragmentCatalogBinding
import com.motionweb.halal.ui.fragment.catalog.adapter.CatalogAdapter
import com.motionweb.halal.ui.fragment.catalog.adapter.ItemClickListener
import com.motionweb.halal.utils.ResultWrapper
import com.motionweb.halal.utils.centeredToast
import com.motionweb.halal.utils.getCurrentDate
import com.motionweb.halal.utils.snackbarExt
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CompaniesFragment : CoreFragment<FragmentCatalogBinding>(), ItemClickListener {

    private val vm: CompaniesVM by viewModels()
    private val args: CompaniesFragmentArgs by navArgs()
    private val companiesAdapter = CatalogAdapter(this)
    private var allCompanies = listOf<Company>()
    private val filteredList = MutableLiveData<List<Company>>()

    override fun createVB() = FragmentCatalogBinding.inflate(layoutInflater)

    override fun setupViews() {
        super.setupViews()
        setupSearch()
        vb.rv.adapter = companiesAdapter
        vb.refresher.setOnRefreshListener { vm.fetchCompaniesByCategoryId(args.categoryId.toInt()) }
        vm.fetchCompaniesByCategoryId(args.categoryId.toInt())
        subscribeToLiveData()
        setupPrayer()
    }

    private fun subscribeToLiveData() {
        vm.allCompanies.observe(viewLifecycleOwner, { result ->
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
                    allCompanies = result.data
                    vb.etSearch.setText("")
                }
            }
            filteredList.observe(viewLifecycleOwner, {
                showEmptyList(it.isEmpty())
                companiesAdapter.submitList(it)
            })
        })
        observeFavorite()
    }

    private fun setupSearch() {
        vb.etSearch.doAfterTextChanged { text ->
            filteredList.value = allCompanies.filter { it.name.contains(text.toString(), true) }
        }
    }

    private fun showEmptyList(value: Boolean) {
        vb.emptyLabel.isVisible = value
        vb.rv.isVisible = !value
    }

    override fun onItemClick(id: Long) {
        findNavController().navigate(CompaniesFragmentDirections.toCompanyDetailFragment(id))
    }

    override fun onFavouriteClick(id: Long, isFavourite: Boolean) {
        if (isFavourite) {
            vm.setFavoriteCompanies(id)
        } else {
            vm.removeFavoriteCompanies(id)
        }
    }

    private fun observeFavorite() {
        vm.favorite.observe(viewLifecycleOwner, { result ->
            when (result) {
                is ResultWrapper.Empty -> {
                }
                is ResultWrapper.Error -> {
                    if (result.code == 401) {
                        vb.root.snackbarExt(R.string.not_registered, R.string.register) {
                            findNavController().navigate(R.id.loginFragment)
                        }
                    } else {
                        requireContext().centeredToast(R.string.error_occurred)
                    }
                }
                ResultWrapper.Loading -> {
                }
                is ResultWrapper.Success -> {
                    requireContext().centeredToast("Успешно добавлено в избранное!")
                }
            }
        })
        vm.isRemoveFavorite.observe(viewLifecycleOwner, { result ->
            when (result) {
                is ResultWrapper.Empty -> {
                }
                is ResultWrapper.Error -> {
                    if (result.code == 401) {
                        vb.root.snackbarExt(R.string.not_registered, R.string.register) {
                            findNavController().navigate(R.id.loginFragment)
                        }
                    } else {
                        requireContext().centeredToast(R.string.error_occurred)
                    }
                }
                ResultWrapper.Loading -> {
                }
                is ResultWrapper.Success -> {
                    requireContext().centeredToast("Успешно удалено из избранных!")
                }
            }
        })
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