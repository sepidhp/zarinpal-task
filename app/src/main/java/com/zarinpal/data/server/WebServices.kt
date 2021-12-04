package com.zarinpal.data.server

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.ApolloQueryCall
import com.zarinpal.RepositoriesQuery
import com.zarinpal.UserInfoQuery
import javax.inject.Inject

class WebServices @Inject constructor(private val apolloClient: ApolloClient) {

    val name : String = "KarimRedaHassan"

    fun getUserInfo(): ApolloQueryCall<UserInfoQuery.Data> =
        apolloClient.query(UserInfoQuery(name))

    fun getRepositories(): ApolloQueryCall<RepositoriesQuery.Data> =
        apolloClient.query(RepositoriesQuery(name))
}