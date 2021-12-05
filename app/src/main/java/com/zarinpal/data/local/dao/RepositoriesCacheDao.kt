package com.zarinpal.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.apollographql.apollo.api.Response
import com.zarinpal.RepositoriesQuery
import com.zarinpal.data.local.tables.RepositoriesCache
import kotlinx.coroutines.flow.Flow

@Dao
interface RepositoriesCacheDao {

    @Query("SELECT * FROM tbl_repositories_cache WHERE cursor=:cursor AND (strftime('%s','now') - createdAt <= :threshold) LIMIT 1")
    fun getRepositories(cursor: String = "", threshold: Int): RepositoriesCache?


    @Query("INSERT INTO tbl_repositories_cache(data , cursor) VALUES (:data , :cursor)")
    fun insert(data: String, cursor: String = "")
}