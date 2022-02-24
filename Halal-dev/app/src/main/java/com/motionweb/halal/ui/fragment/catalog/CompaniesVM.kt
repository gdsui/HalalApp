package com.motionweb.halal.ui.fragment.catalog

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.motionweb.halal.core.ui.viewmodel.CoreVM
import com.motionweb.halal.data.network.catalog.models.Company
import com.motionweb.halal.data.network.favorite.FavoriteResponseModel
import com.motionweb.halal.data.storage.db.FavoriteEntity
import com.motionweb.halal.repository.CatalogRepository
import com.motionweb.halal.utils.ResultWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompaniesVM @Inject constructor(private val repository: CatalogRepository) : CoreVM() {

    var isInit: Boolean = false
        private set

    init {
        isInit = true
    }

    private val _allCompanies: MutableLiveData<ResultWrapper<List<Company>>> = MutableLiveData()
    val allCompanies: LiveData<ResultWrapper<List<Company>>> get() = _allCompanies

    val favorite: MutableLiveData<ResultWrapper<FavoriteResponseModel>> = MutableLiveData()
    val isRemoveFavorite: MutableLiveData<ResultWrapper<FavoriteResponseModel>> = MutableLiveData()

    val prayerTime: String = repository.prayerTime
    val nearestPrayer: String = repository.nearestPrayer

    fun fetchCompaniesByCategoryId(id: Int) {
        viewModelScope.launch {
            _allCompanies.value = ResultWrapper.loading()
            try {
                val companies = async { repository.fetchCompaniesByCategoryId(id) }
                val favorites = async { repository.fetchFavorites() }
                val filteredFavorites = favorites.await().map { it.id }

                for (filteredFavorite in filteredFavorites) {
                    for (i in companies.await()) {
                        if (filteredFavorite == i.id) {
                            i.isFavorite = true
                        }
                    }
                }
                _allCompanies.value = ResultWrapper.successOrEmpty(companies.await())
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
                _allCompanies.value = ResultWrapper.Error(e)
            }
        }
    }

    fun setFavoriteCompanies(id: Long) {
        favorite.value = ResultWrapper.loading()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.fetchProfile()
                val favs = response?.favoriteCompanies?.toMutableList()
                favs?.add(id.toInt())
                favs?.toSet()
                if (favs != null) {
                    val result = repository.setFavoriteCompanies(favs)
                    favorite.postValue(ResultWrapper.success(result))
                }
            } catch (e: Exception) {
                e.printStackTrace()
                favorite.postValue(ResultWrapper.error(e))
            }
        }
    }

    fun removeFavoriteCompanies(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.fetchProfile()
                val favs = response?.favoriteCompanies?.toMutableList()
                favs?.add(id.toInt())
                favs?.toSet()
                if (favs != null) {
                    val result = repository.setFavoriteCompanies(favs)
                    isRemoveFavorite.postValue(ResultWrapper.success(result))
                }
            } catch (e: Exception) {
                e.printStackTrace()
                isRemoveFavorite.postValue(ResultWrapper.error(e))
            }
        }
    }


}