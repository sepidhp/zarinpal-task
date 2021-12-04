package com.zarinpal.modules.userInfo.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo.coroutines.await
import com.zarinpal.UserInfoQuery
import com.zarinpal.data.server.CallException
import com.zarinpal.modules.userInfo.repository.UserInfoRepository
import com.zarinpal.utils.CredentialManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class UserInfoViewModel @Inject constructor(private val repository: UserInfoRepository) :
    ViewModel() {

    @Inject
    lateinit var credentialManager: CredentialManager

    private val _isApiCalling = MutableLiveData<Boolean>()
    val isApiCalling: LiveData<Boolean> = _isApiCalling

    private val _apiException = MutableLiveData<Throwable>()
    val apiException: LiveData<Throwable> = _apiException

    private val _userInfo = MutableLiveData<UserInfoQuery.User>()
    val userInfo: LiveData<UserInfoQuery.User> = _userInfo

    fun getUserInfo() {

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

            val result = repository.getUserInfo().await()

            withContext(Dispatchers.Main) {

                _isApiCalling.value = false

                _userInfo.value = result.data?.user
            }
        }
    }
}