package com.example.owentime.base

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BaseResponse<T>(
    @SerialName("code")
    val code:Int=0,
    @SerialName("message")
    val message:String="",
    @SerialName("data")
    val data:T,
    @SerialName("status_code")
    val status_code:Int
    )
