package com.motionweb.halal.repository

import com.motionweb.halal.data.network.catalog.CatalogApi
import com.motionweb.halal.data.network.catalog.models.CompanyDetail
import com.motionweb.halal.data.network.favorite.FavoriteApi
import com.motionweb.halal.data.network.favorite.FavoriteRequestModel
import com.motionweb.halal.data.network.favorite.FavoriteResponseModel
import com.motionweb.halal.data.network.profile.ProfileApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface FavoriteRepository {
    suspend fun addFavorites(favoriteCompanies: FavoriteRequestModel): FavoriteResponseModel
    suspend fun fetchAllFavoriteCompanies(): Flow<List<CompanyDetail?>?>
}


class FavoriteRepositoryImpl @Inject constructor(
    private val api: ProfileApi,
    private val favoriteApi: FavoriteApi,
    private val catalogApi: CatalogApi
) : FavoriteRepository {

    private suspend fun fetchUserFavoritesId(): List<FavoriteCompaniesId>? =
        api.fetchProfile()?.favoriteCompanies?.map { FavoriteCompaniesId(it) }

    private suspend fun fetchCompanyById(id: Int): CompanyDetail =
        catalogApi.fetchCompanyDetail(id)

    override suspend fun addFavorites(favoriteCompanies: FavoriteRequestModel): FavoriteResponseModel =
        favoriteApi.addFavorite(favoriteCompanies)

    override suspend fun fetchAllFavoriteCompanies(): Flow<List<CompanyDetail?>?> =
        flow<List<CompanyDetail?>?> {
            emit(fetchUserFavoritesId()?.map { fetchCompanyById(it.id) })
        }
}

data class FavoriteCompaniesId(
    val id: Int
)