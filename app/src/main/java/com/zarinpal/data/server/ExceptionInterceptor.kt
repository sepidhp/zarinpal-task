package com.zarinpal.data.server

import okhttp3.Interceptor
import okhttp3.Response

class ExceptionInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        try {

            val request = chain.request()
            val response = chain.proceed(request)

            return if (response.isSuccessful)
                response
            else
                throw CallException(response.code, response.message, response.body?.string())

        } catch (exception: Exception) {
            exception.printStackTrace()
            throw exception
        }
    }
}