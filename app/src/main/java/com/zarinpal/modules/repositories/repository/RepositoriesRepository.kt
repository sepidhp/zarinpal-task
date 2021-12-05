package com.zarinpal.modules.repositories.repository

import com.apollographql.apollo.api.Response
import com.apollographql.apollo.api.toJson
import com.zarinpal.RepositoriesQuery
import com.zarinpal.data.local.dao.RepositoriesCacheDao
import com.zarinpal.data.server.WebServices
import com.zarinpal.extension.toMyData
import javax.inject.Inject

class RepositoriesRepository @Inject constructor(
    private val webServices: WebServices,
    private val local: RepositoriesCacheDao
) {
    private val threshold = 120 // 2 minute

    suspend fun getRepositories(cursor: String?): Response<RepositoriesQuery.Data> {

        val cachedData = local.getRepositories(cursor ?: "", threshold)

        if (cachedData != null)
            return cachedData.data.toMyData(cursor)!!

        val serverData = webServices.getRepositories(cursor)
        local.insert(serverData.data!!.toJson(), cursor ?: "")
        return serverData
    }
}