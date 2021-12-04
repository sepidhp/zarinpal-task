package com.zarinpal.modules.userInfo.repository

import com.apollographql.apollo.ApolloQueryCall
import com.zarinpal.UserInfoQuery
import com.zarinpal.data.server.WebServices
import javax.inject.Inject

class UserInfoRepository @Inject constructor(private val webServices: WebServices) {

    fun getUserInfo(name: String): ApolloQueryCall<UserInfoQuery.Data> =
        webServices.getUserInfo(name)
}