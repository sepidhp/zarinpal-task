package com.zarinpal.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.zarinpal.data.local.dao.RepositoriesCacheDao
import com.zarinpal.data.local.dao.UserInfoCacheDao
import com.zarinpal.data.local.tables.RepositoriesCache
import com.zarinpal.data.local.tables.UserInfoCache

@Database(
    entities = [RepositoriesCache::class, UserInfoCache::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun repositoriesCacheDao(): RepositoriesCacheDao
    abstract fun userInfoCacheDao(): UserInfoCacheDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {

            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "zarinpal.db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}