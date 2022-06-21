package com.example.owentime.base

import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(
    @SerializedName("code")
    val code: Int? = 0,
    @SerializedName("data")
    val data: T?=null,
    @SerializedName("message")
    val message: String? = "",
    @SerializedName("status_code")
    val statusCode: Int? = 0
    )
