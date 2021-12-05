package com.zarinpal.data.local.tables

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_user_cache")
data class UserInfoCache(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val data: String,
    @ColumnInfo(defaultValue = "(strftime('%s','now'))")
    val createdAt: Long
)