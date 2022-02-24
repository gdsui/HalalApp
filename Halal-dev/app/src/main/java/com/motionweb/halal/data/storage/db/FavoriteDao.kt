package com.motionweb.halal.data.storage.db

import androidx.room.*

@Dao
interface FavoriteDao {

    @Query("SELECT * FROM favorite")
    suspend fun fetchAllFavorites(): List<FavoriteEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(favoriteEntity: FavoriteEntity)

    @Delete
    suspend fun deleteFavorite(favoriteEntity: FavoriteEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllFavorite(favoriteEntity: List<FavoriteEntity>)

    @Delete
    suspend fun deleteAllFavorites(favoriteEntity: List<FavoriteEntity>)

    @Query("DELETE FROM favorite")
    suspend fun clearAll()

}