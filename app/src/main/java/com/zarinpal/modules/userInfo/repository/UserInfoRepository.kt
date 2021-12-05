package com.zarinpal.modules.userInfo.repository

import com.apollographql.apollo.api.Response
import com.apollographql.apollo.api.ScalarTypeAdapters
import com.apollographql.apollo.api.toJson
import com.apollographql.apollo.response.OperationResponseParser
import com.zarinpal.UserInfoQuery
import com.zarinpal.data.local.dao.UserInfoCacheDao
import com.zarinpal.data.server.WebServices
import com.zarinpal.utils.QueryFields
import okio.Buffer
import javax.inject.Inject

class UserInfoRepository @Inject constructor(
    private val webServices: WebServices,
    private val local: UserInfoCacheDao
) {
    private val threshold = 120 // 2 minute

    suspend fun getUserInfo(): Response<UserInfoQuery.Data> {

        val cachedData = local.getUserInfo(threshold)
        if (cachedData != null)
            return convertStingToMyData(cachedData.data)!!


        val serverData = webServices.getUserInfo()
        local.insert(serverData.data!!.toJson())
        return serverData
    }
}

private fun convertStingToMyData(value: String?): Response<UserInfoQuery.Data>? {
    value ?: return null
    val query = UserInfoQuery(QueryFields.USERNAME)
    return OperationResponseParser(
        query,
        query.responseFieldMapper(),
        ScalarTypeAdapters.DEFAULT
    ).parse(Buffer().writeUtf8(value))
}