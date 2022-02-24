package com.motionweb.halal.data.network.catalog

import com.motionweb.halal.data.network.catalog.models.Category
import com.motionweb.halal.data.network.catalog.models.Company
import com.motionweb.halal.data.network.catalog.models.CompanyDetail
import com.motionweb.halal.data.network.catalog.models.Product
import retrofit2.http.GET
import retrofit2.http.Path

interface CatalogApi {

    @GET("companies/category/all")
    suspend fun fetchAllCategories(): List<Category>

    @GET("companies/company/all")
    suspend fun fetchAllCompanies(): List<Company>

    @GET("companies/company/{company_id}/products")
    suspend fun fetchCompanyProducts(@Path("company_id") companyId: Int): List<Product>

    @GET("companies/company/{id}")
    suspend fun fetchCompanyDetail(@Path("id") companyId: Int): CompanyDetail

    @GET("companies/category/{category_id}/companies/")
    suspend fun fetchCompaniesByCategoryId(@Path("category_id") categoryId: Int): List<Company>

}