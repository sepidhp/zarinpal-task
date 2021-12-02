package com.zarinpal.data.server

import java.io.IOException

data class CallException(
    var responseCode: Int,
    var responseMessage: String,
    var responseBody: String?
) : IOException("Error $responseCode : $responseMessage")