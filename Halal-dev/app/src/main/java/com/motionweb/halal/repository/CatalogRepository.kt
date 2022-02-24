package com.motionweb.halal.repository

import com.motionweb.halal.data.network.catalog.CatalogApi
import com.motionweb.halal.data.network.catalog.models.Category
import com.motionweb.halal.data.network.catalog.models.Company
import com.motionweb.halal.data.network.catalog.models.CompanyDetail
import com.motionweb.halal.data.network.catalog.models.Product
import com.motionweb.halal.data.network.favorite.FavoriteApi
import com.motionweb.halal.data.network.favorite.FavoriteRequestModel
import com.motionweb.halal.data.network.favorite.FavoriteResponseModel
import com.motionweb.halal.data.network.profile.ProfileApi
import com.motionweb.halal.data.network.profile.UserModel
import com.motionweb.halal.data.storage.db.FavoriteDao
import com.motionweb.halal.data.storage.db.FavoriteEntity
import com.motionweb.halal.data.storage.sharedPref.AppPreferences
import javax.inject.Inject

interface CatalogRepository {
    suspend fun fetchAllCategories(): List<Category>
    suspend fun fetchAllCompanies(): List<Company>
    suspend fun fetchCompanyProducts(companyId: Int): List<Product>
    suspend fun fetchCompanyDetail(companyId: Int): CompanyDetail
    suspend fun fetchCompaniesByCategoryId(categoryId: Int): List<Company>
    suspend fun setFavoriteCompanies(favoriteCompanies: List<Int>): FavoriteResponseModel
    suspend fun fetchFavorites(): List<FavoriteEntity>
    suspend fun addFavorite(favorite: FavoriteEntity)
    suspend fun removeFavorite(favorite: FavoriteEntity)
    suspend fun addAllFavorite(favorite: List<FavoriteEntity>)
    suspend fun deleteAllFavorite(favorite: List<FavoriteEntity>)

    fun setFavorite(favorites: String)
    suspend fun fetchProfile(): UserModel?

    val prayerTime: String
    val nearestPrayer: String
    val favorites: String
}

class CatalogRepositoryImpl @Inject constructor(
    private val api: CatalogApi,
    private val favoriteApi: FavoriteApi,
    private val appPrefs: AppPreferences,
    private val dao: FavoriteDao,
    private val profileApi: ProfileApi
) : CatalogRepository {

    override val prayerTime: String = appPrefs.prayerTime

    override val nearestPrayer: String = appPrefs.nearestPrayer

    override val favorites: String
        get() = appPrefs.favorites

    override suspend fun fetchAllCategories(): List<Category> =
        api.fetchAllCategories()

    override suspend fun fetchAllCompanies(): List<Company> =
        api.fetchAllCompanies()

    override suspend fun fetchCompanyProducts(companyId: Int): List<Product> =
        api.fetchCompanyProducts(companyId)

    override suspend fun fetchCompanyDetail(companyId: Int): CompanyDetail =
        api.fetchCompanyDetail(companyId)

    override suspend fun fetchCompaniesByCategoryId(categoryId: Int): List<Company> =
        api.fetchCompaniesByCategoryId(categoryId)

    override suspend fun setFavoriteCompanies(favoriteCompanies: List<Int>): FavoriteResponseModel {
        return favoriteApi.addFavorite(FavoriteRequestModel(favoriteCompanies))
    }

    override fun setFavorite(favorites: String) {
        appPrefs.favorites = favorites
    }

    override suspend fun fetchFavorites(): List<FavoriteEntity> = dao.fetchAllFavorites()

    override suspend fun addFavorite(favorite: FavoriteEntity) {
        dao.addFavorite(favorite)
    }

    override suspend fun removeFavorite(favorite: FavoriteEntity) {
        dao.deleteFavorite(favorite)
    }

    override suspend fun addAllFavorite(favorite: List<FavoriteEntity>) {
        dao.addAllFavorite(favorite)
    }

    override suspend fun deleteAllFavorite(favorite: List<FavoriteEntity>) {
        dao.deleteAllFavorites(favorite)
    }

    override suspend fun fetchProfile(): UserModel? = profileApi.fetchProfile()

}