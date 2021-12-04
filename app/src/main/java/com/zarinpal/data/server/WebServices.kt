package com.zarinpal.data.server

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.ApolloQueryCall
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.api.toInput
import com.zarinpal.RepositoriesQuery
import com.zarinpal.UserInfoQuery
import javax.inject.Inject

class WebServices @Inject constructor(private val apolloClient: ApolloClient) {

    val username = "KarimRedaHassan"
    val repositoryCount = 5
    val repositoryTopicCount = 10

    fun getUserInfo(): ApolloQueryCall<UserInfoQuery.Data> =
        apolloClient.query(UserInfoQuery(username))

    fun getRepositories(cursor: String?): ApolloQueryCall<RepositoriesQuery.Data> =
        apolloClient.query(
            RepositoriesQuery(
                repositoryCount,
                repositoryTopicCount,
                username,
                cursor.toInput()
            )
        )
}