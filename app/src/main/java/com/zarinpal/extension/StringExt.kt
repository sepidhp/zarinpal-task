package com.zarinpal.extension

import com.apollographql.apollo.api.Response
import com.apollographql.apollo.api.ScalarTypeAdapters
import com.apollographql.apollo.api.toInput
import com.apollographql.apollo.response.OperationResponseParser
import com.zarinpal.RepositoriesQuery
import com.zarinpal.utils.QueryFields
import okio.Buffer

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