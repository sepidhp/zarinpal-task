package com.zarinpal.data.server

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.ApolloQueryCall
import com.apollographql.apollo.api.internal.ApolloLogger
import com.zarinpal.RepositoriesQuery
import com.zarinpal.UserInfoQuery
import com.zarinpal.fragment.RepositoryFragment
import com.zarinpal.type.OrderDirection
import com.zarinpal.type.RepositoryOrderField
import dagger.Module
import dagger.hilt.InstallIn
import javax.inject.Inject

class WebServices @Inject constructor(private val apolloClient: ApolloClient) {

    fun getUserInfo(name: String): ApolloQueryCall<UserInfoQuery.Data> =
        apolloClient.query(UserInfoQuery(name))

    fun getRepositories(): ApolloQueryCall<RepositoriesQuery.Data> =
        apolloClient.query(
            RepositoriesQuery(
                10,
                RepositoryOrderField.CREATED_AT,
                OrderDirection.DESC
            )
        )
}