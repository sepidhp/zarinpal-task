package com.zarinpal.data.server

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody
import okio.Buffer

class LoggingInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()
        val response = chain.proceed(request)
        val responseBody = response.body?.string()

        Log.i("API_LOG", createLog(request, response, responseBody))

        val wrappedBody = ResponseBody.create(response.body?.contentType(), responseBody!!)
        return response.newBuilder().body(wrappedBody).build()
    }

    private fun createLog(request: Request, response: Response, responseBody: String?): String {

        val requestBuffer = Buffer()
        request.body?.writeTo(requestBuffer)

        val stringBuilder = StringBuilder()

        stringBuilder.append(" ")
        stringBuilder.append("\n")
        stringBuilder.append("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ API Log ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~")
        stringBuilder.append("\n")
        stringBuilder.append("[${response.request.method}] ${response.request.url}")
        stringBuilder.append("\n")
        stringBuilder.append("Token : ${response.request.header("Authorization")}")
        stringBuilder.append("\n")
        stringBuilder.append("Request Body : ${requestBuffer.readUtf8()}")
        stringBuilder.append("\n")
        stringBuilder.append("Response Code : ${response.code}")
        stringBuilder.append("\n")
        stringBuilder.append("Response Message : ${response.message}")
        stringBuilder.append("\n")
        stringBuilder.append("Response Body : $responseBody")
        stringBuilder.append("\n")
        stringBuilder.append("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~")

        return stringBuilder.toString()
    }
}