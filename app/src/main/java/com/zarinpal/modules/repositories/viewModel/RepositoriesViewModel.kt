package com.zarinpal.modules.repositories.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo.ApolloQueryCall
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.api.ScalarTypeAdapters
import com.apollographql.apollo.api.toJson
import com.apollographql.apollo.coroutines.await
import com.apollographql.apollo.response.OperationResponseParser
import com.zarinpal.RepositoriesQuery
import com.zarinpal.UserInfoQuery
import com.zarinpal.data.server.CallException
import com.zarinpal.fragment.RepositoryFragment
import com.zarinpal.modules.repositories.repository.RepositoriesRepository
import com.zarinpal.utils.CredentialManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.security.PublicKey
import javax.inject.Inject

@HiltViewModel
class RepositoriesViewModel @Inject constructor(private val repository: RepositoriesRepository) :
    ViewModel() {

    @Inject
    lateinit var credentialManager: CredentialManager

    private val _isApiCalling = MutableLiveData<Boolean>()
    val isApiCalling: LiveData<Boolean> = _isApiCalling

    private val _apiException = MutableLiveData<Throwable>()
    val apiException: LiveData<Throwable> = _apiException

    private val _repositoryInfo = MutableLiveData<RepositoriesQuery.Repositories>()
    val repositoryInfo: LiveData<RepositoriesQuery.Repositories> = _repositoryInfo

    fun getRepositoriesApi(cursor: String?) {

        _isApiCalling.value = true

        val exceptionHandler = CoroutineExceptionHandler { _, exception ->

            exception.printStackTrace()

            viewModelScope.launch(Dispatchers.Main) {

                _isApiCalling.value = false

                if (exception is CallException && exception.responseCode == 401)
                    credentialManager.reLogin()
                else
                    _apiException.value = exception
            }
        }

        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {

            val result = repository.getRepositories(cursor)

            withContext(Dispatchers.Main) {

                _isApiCalling.value = false

                _repositoryInfo.value = result.data?.repositoryOwner?.repositories
            }
        }
    }

    fun getRepositories(result: RepositoriesQuery.Repositories) =
        result.nodes?.mapNotNull { it?.fragments?.repositoryFragment }
            ?: emptyList()
}