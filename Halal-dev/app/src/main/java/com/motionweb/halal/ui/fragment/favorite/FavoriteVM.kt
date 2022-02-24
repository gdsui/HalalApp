package com.motionweb.halal.ui.fragment.favorite

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.motionweb.halal.core.ui.viewmodel.CoreVM
import com.motionweb.halal.data.network.catalog.models.CompanyDetail
import com.motionweb.halal.data.network.favorite.FavoriteResponseModel
import com.motionweb.halal.data.storage.db.FavoriteEntity
import com.motionweb.halal.repository.CatalogRepository
import com.motionweb.halal.repository.FavoriteRepository
import com.motionweb.halal.utils.ResultWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteVM @Inject constructor(
    private val repository: FavoriteRepository,
    private val catalogRepository: CatalogRepository
) : CoreVM() {

    val allFavorites: MutableStateFlow<ResultWrapper<List<CompanyDetail?>?>> = MutableStateFlow(
        ResultWrapper.success(emptyList())
    )

    val favorite: MutableLiveData<ResultWrapper<FavoriteResponseModel>> = MutableLiveData()
    val isRemoveFavorite: MutableLiveData<ResultWrapper<FavoriteResponseModel>> = MutableLiveData()

    fun getAllFavoriteCompanies() {
        viewModelScope.launch {
            try {
                allFavorites.emit(ResultWrapper.loading())
                repository.fetchAllFavoriteCompanies()
                    .catch { allFavorites.value = ResultWrapper.error(it) }
                    .collect {
                        if (it?.isEmpty() == true) {
                            allFavorites.value = ResultWrapper.empty(it)
                        } else allFavorites.value = ResultWrapper.success(it)
                    }
            } catch (t: Throwable) {
                allFavorites.value = ResultWrapper.error(t)
            }
        }
    }

    fun setFavoriteCompanies(companies: List<Int>) {
        favorite.value = ResultWrapper.loading()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val oldFavs: List<Int> = catalogRepository.fetchFavorites().map { it.id }
                val response =
                    catalogRepository.setFavoriteCompanies((companies + oldFavs).toSet().toList())
                favorite.postValue(ResultWrapper.success(response))
                catalogRepository.addAllFavorite(response.favoriteCompanies.map { FavoriteEntity(it) })
            } catch (e: Exception) {
                e.printStackTrace()
                favorite.postValue(ResultWrapper.error(e))
            }
        }
    }

    fun removeFavoriteCompanies(companies: List<Int>) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val oldFavs: List<Int> =
                    catalogRepository.fetchFavorites().map { it.id }.filter { it != companies[0] }
                val response =
                    catalogRepository.setFavoriteCompanies((oldFavs).toSet().toList())
                catalogRepository.deleteAllFavorite(companies.map { FavoriteEntity(it) })
                catalogRepository.addAllFavorite(response.favoriteCompanies.map { FavoriteEntity(it) })
                isRemoveFavorite.postValue(ResultWrapper.success(response))
            } catch (e: Exception) {
                e.printStackTrace()
                isRemoveFavorite.postValue(ResultWrapper.error(e))
            }
        }
    }

}