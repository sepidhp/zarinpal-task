package com.zarinpal.utils

import android.content.Context
import javax.inject.Inject

class CredentialManager @Inject constructor(private val context: Context) {

    private val preferencesHelper: SharedPreferencesHelper
    private val preferencesFile = "credential_data"

    init {
        preferencesHelper = SharedPreferencesHelper(context, preferencesFile)
    }

    fun deleteCredential() {
        preferencesHelper.clear()
    }

    fun reLogin() {
        deleteCredential()
    }
}