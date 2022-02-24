package com.motionweb.halal.data.network.favorite

import retrofit2.http.Body
import retrofit2.http.PATCH

interface FavoriteApi {

    @PATCH("auth/favorite/")
    suspend fun addFavorite(
        @Body favoriteCompanies: FavoriteRequestModel
    ): FavoriteResponseModel

}
