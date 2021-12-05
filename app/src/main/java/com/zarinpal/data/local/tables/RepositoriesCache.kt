package com.zarinpal.data.local.tables

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.apollographql.apollo.ApolloQueryCall
import com.zarinpal.RepositoriesQuery

@Entity(tableName = "tbl_repositories_cache")
data class RepositoriesCache(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val data: String,
    val cursor: String,
    @ColumnInfo(defaultValue = "(strftime('%s','now'))")
    val createdAt: Long
)