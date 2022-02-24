package com.motionweb.halal.ui.fragment.favorite

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.motionweb.halal.R
import com.motionweb.halal.core.ui.fragment.CoreFragment
import com.motionweb.halal.databinding.FragmentFavoriteBinding
import com.motionweb.halal.ui.fragment.catalog.adapter.ItemClickListener
import com.motionweb.halal.ui.fragment.favorite.adapter.FavoriteAdapter
import com.motionweb.halal.utils.ResultWrapper
import com.motionweb.halal.utils.centeredToast
import com.motionweb.halal.utils.snackbarExt
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class FavoriteFragment : CoreFragment<FragmentFavoriteBinding>(), ItemClickListener {

    private val vm: FavoriteVM by viewModels()
    private val favoriteAdapter = FavoriteAdapter(this)

    override fun createVB(): FragmentFavoriteBinding =
        FragmentFavoriteBinding.inflate(layoutInflater)

    override fun setupViews() {
        super.setupViews()
        vb.rvFavorite.adapter = favoriteAdapter
        vm.getAllFavoriteCompanies()
        subscribeToFavorites()
        observeFavorite()
    }

    private fun subscribeToFavorites() {
        lifecycleScope.launchWhenCreated {
            vm.allFavorites.collect { result ->
                when (result) {
                    is ResultWrapper.Empty -> {
                        requireContext().centeredToast(R.string.empty_favorites)
                        vb.progress.isVisible = false
                    }
                    is ResultWrapper.Error -> {
                        vb.progress.isVisible = false
                        if (result.code == 401) {
                            Snackbar.make(
                                vb.root,
                                getString(R.string.not_registered),
                                Snackbar.LENGTH_LONG
                            ).setAction(R.string.register) {
                                findNavController().navigate(R.id.loginFragment)
                            }.show()
                        }
                    }
                    ResultWrapper.Loading -> {
                        vb.progress.isVisible = true
                    }
                    is ResultWrapper.Success -> {
                        favoriteAdapter.submitCompanies(result.data)
                        vb.progress.isVisible = false
                    }
                }
            }
        }
    }

    override fun onFavouriteClick(id: Long, isFavourite: Boolean) {
        if (isFavourite) {
            vm.setFavoriteCompanies(listOf(id.toInt()))
        } else {
            vm.removeFavoriteCompanies(listOf(id.toInt()))
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
                    vm.getAllFavoriteCompanies()
                }
            }
        })
    }

}