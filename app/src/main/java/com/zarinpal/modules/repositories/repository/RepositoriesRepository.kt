package com.zarinpal.modules.repositories.repository

import com.zarinpal.data.server.WebServices
import javax.inject.Inject

class RepositoriesRepository @Inject constructor(private val webServices: WebServices) {

    fun getRepositories(cursor : String?) = webServices.getRepositories(cursor)
}