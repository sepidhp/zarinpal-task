package com.zarinpal.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.zarinpal.data.local.tables.UserInfoCache

@Dao
interface UserInfoCacheDao {

    @Query("SELECT * FROM tbl_user_cache WHERE (strftime('%s','now') - createdAt <= :threshold) LIMIT 1")
    fun getUserInfo(threshold: Int): UserInfoCache?

    @Query("INSERT INTO tbl_user_cache(data) VALUES (:data)")
    fun insert(data: String)
}