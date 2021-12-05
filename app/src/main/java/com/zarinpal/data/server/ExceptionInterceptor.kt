package com.zarinpal.data.server

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class ExceptionInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        try {
            val request = chain.request()
            val builder: Request.Builder =
                request.newBuilder().method(request.method, request.body)
            builder.header("Authorization", "bearer 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")
            val response = chain.proceed(builder.build())

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