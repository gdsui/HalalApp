package com.motionweb.halal.ui.fragment.ecode

import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import com.motionweb.halal.R
import com.motionweb.halal.core.ui.fragment.CoreFragment
import com.motionweb.halal.data.network.ecode.ECode
import com.motionweb.halal.databinding.FragmentEcodeBinding
import com.motionweb.halal.ui.fragment.ecode.adapter.ECodeAdapter
import com.motionweb.halal.utils.ResultWrapper
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class ECodeFragment : CoreFragment<FragmentEcodeBinding>(), ECodeAdapter.ECodeListener {

    private val vm: ECodeVM by viewModels()
    private val eCodeAdapter = ECodeAdapter(this)
    private var allECodes = listOf<ECode>()
    private val filteredECodeList = MutableLiveData<List<ECode>>()

    override fun createVB(): FragmentEcodeBinding = FragmentEcodeBinding.inflate(layoutInflater)

    override fun setupViews() {
        vb.rvEcode.adapter = eCodeAdapter
        subscribeToLiveData()
        vb.refresher.setOnRefreshListener { vm.fetchAllECodes() }
        vm.fetchAllECodes()
        setupSearch()
        setupPrayerTime()
    }

    private fun setupSearch() {
        vb.etSearch.doAfterTextChanged { text ->
            filteredECodeList.value = allECodes.filter { it.code.contains(text.toString(), true) }
        }
    }

    private fun setupPrayerTime() {
        vb.prayerView.setBottomText(getCurrentDate().toString())
        vb.prayerView.setPrayerTopText(vm.prayerTime)
        vb.prayerView.setPrayerBottomText(vm.nearestPrayer)
        vb.prayerView.setPrayerClickListener {
            findNavController().navigate(R.id.prayerTimeFragment)
        }
        vb.prayerView.setCalendarClickListener {
            findNavController().navigate(R.id.islamicCalendarFragment)
        }
    }

    private fun subscribeToLiveData() {
        vm.allECodes.observe(viewLifecycleOwner) { result ->
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
                    allECodes = result.data
                    vb.etSearch.setText("")
                }
            }
        }
        filteredECodeList.observe(viewLifecycleOwner) {
            showEmptyList(it.isEmpty())
            eCodeAdapter.submitItems(it)
        }
    }

    private fun showEmptyList(value: Boolean) {
        vb.emptyLabel.isVisible = value
        vb.rvEcode.isVisible = !value
    }

    override fun onEcodeClick(eCode: ECode) {
        val direction = ECodeFragmentDirections.actionECodeFragmentToECodeDetailFragment(eCode)
        findNavController().navigate(direction)
    }

    private fun getCurrentDate(): String? {
        val date = SimpleDateFormat("EEE, d MMM ''yy", Locale.getDefault())
        return date.format(Date())
    }

}
