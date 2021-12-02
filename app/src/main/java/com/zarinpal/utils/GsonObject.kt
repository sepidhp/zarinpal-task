package com.zarinpal.utils

import com.google.gson.Gson
import com.google.gson.GsonBuilder

object GsonObject {
    val gson : Gson = GsonBuilder().serializeNulls().create()
}