package com.motionweb.halal.ui.fragment.event

import android.content.Intent
import android.net.Uri
import androidx.fragment.app.viewModels
import com.motionweb.halal.core.ui.fragment.CoreFragment
import com.motionweb.halal.databinding.FragmentEventBinding
import com.motionweb.halal.ui.activity.main.MainActivity
import com.motionweb.halal.utils.ResultWrapper
import com.motionweb.halal.utils.parentActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EventFragment : CoreFragment<FragmentEventBinding>() {

    private val vm: EventVM by viewModels()
    private val eventAdapter = EventAdapter()

    override fun createVB(): FragmentEventBinding = FragmentEventBinding.inflate(layoutInflater)

    override fun setupViews() {
        super.setupViews()
        vb.rv.adapter = eventAdapter
        subscribeToLiveData()
        vm.fetchEvents()
        vb.ivPhone.setOnClickListener {
            openPhone()
        }
        vb.tvNumberTitle.setOnClickListener {
            openPhone()
        }
    }

    private fun openPhone() {
        val phoneUri = Uri.parse("tel:" + "996550012208")
        val intent = Intent(Intent.ACTION_DIAL, phoneUri)
        try {
            startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun subscribeToLiveData() {
        vm.events.observe(viewLifecycleOwner) { result ->
            when (result) {
                is ResultWrapper.Empty -> {
                }
                is ResultWrapper.Error -> {
                }
                is ResultWrapper.Loading -> {
                }
                is ResultWrapper.Success -> {
                    eventAdapter.submitList(result.data)
                }
            }
        }
    }
}