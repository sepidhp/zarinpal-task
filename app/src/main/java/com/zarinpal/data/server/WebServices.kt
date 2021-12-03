package com.zarinpal.data.server

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.ApolloQueryCall
import com.apollographql.apollo.api.internal.ApolloLogger
import com.zarinpal.UserInfoQuery
import dagger.Module
import dagger.hilt.InstallIn
import javax.inject.Inject

class WebServices @Inject constructor(private val apolloClient: ApolloClient) {

    fun getUserInfo(): ApolloQueryCall<UserInfoQuery.Data> = apolloClient.query(UserInfoQuery())
}