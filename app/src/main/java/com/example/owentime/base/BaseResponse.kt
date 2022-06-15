package com.example.owentime.base

import com.example.owentime.EmptyStringToOjb
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BaseResponse<T>(
    @Json(name = "code")
    val code: Int? = 0,
    @Json(name = "data")
    val data: T?=null,
    @Json(name = "message")
    val message: String? = "",
    @Json(name = "status_code")
    val statusCode: Int? = 0
    )
