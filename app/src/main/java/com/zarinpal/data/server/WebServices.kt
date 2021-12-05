package com.zarinpal.data.server

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.ApolloQueryCall
import com.apollographql.apollo.api.*
import com.apollographql.apollo.coroutines.await
import com.zarinpal.RepositoriesQuery
import com.zarinpal.UserInfoQuery
import com.zarinpal.utils.QueryFields
import javax.inject.Inject

class WebServices @Inject constructor(private val apolloClient: ApolloClient) {

    suspend fun getUserInfo(): Response<UserInfoQuery.Data> =
        apolloClient.query(UserInfoQuery(QueryFields.USERNAME)).await()

    suspend fun getRepositories(cursor: String?): Response<RepositoriesQuery.Data> =
        apolloClient.query(
            RepositoriesQuery(
                QueryFields.REPOSITORY_COUNT,
                QueryFields.TOPIC_COUNT,
                QueryFields.USERNAME,
                cursor.toInput()
            )
        ).await()
}