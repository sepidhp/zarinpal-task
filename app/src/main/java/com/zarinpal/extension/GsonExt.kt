package com.zarinpal.extension

import com.google.gson.reflect.TypeToken
import com.zarinpal.utils.GsonObject

inline fun <reified T> String?.fromJson(): T = GsonObject.gson.fromJson(this, object : TypeToken<T>() {}.type)

fun Any.toJson(): String = GsonObject.gson.toJson(this)