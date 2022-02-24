package com.motionweb.halal.data.storage.db

import android.content.Context
import androidx.room.*

@Database(entities = [FavoriteEntity::class], version = 1, exportSchema = false)
abstract class HalalDB : RoomDatabase() {

    abstract fun favoriteDao(): FavoriteDao

    companion object {
        private const val DB_NAME: String = "halalDB"
        private var instance: HalalDB? = null

        fun getInstance(context: Context): HalalDB {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    HalalDB::class.java,
                    DB_NAME
                ).fallbackToDestructiveMigration().build()
            }
            return instance!!
        }
    }
}

@Entity(tableName = "favorite")
data class FavoriteEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int
)