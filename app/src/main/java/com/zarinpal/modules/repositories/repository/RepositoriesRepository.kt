package com.zarinpal.modules.repositories.repository

import com.apollographql.apollo.api.Response
import com.apollographql.apollo.api.ScalarTypeAdapters
import com.apollographql.apollo.api.toInput
import com.apollographql.apollo.api.toJson
import com.apollographql.apollo.response.OperationResponseParser
import com.zarinpal.RepositoriesQuery
import com.zarinpal.data.local.dao.RepositoriesCacheDao
import com.zarinpal.data.server.WebServices
import com.zarinpal.utils.QueryFields
import okio.Buffer
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

fun String?.toMyData(cursor: String?): Response<RepositoriesQuery.Data>? {
    this ?: return null
    val query = RepositoriesQuery(
        QueryFields.REPOSITORY_COUNT,
        QueryFields.TOPIC_COUNT,
        QueryFields.USERNAME,
        cursor.toInput()
    )
    return OperationResponseParser(
        query,
        query.responseFieldMapper(),
        ScalarTypeAdapters.DEFAULT
    ).parse(Buffer().writeUtf8(this))
}