package com.example.owentime.base

import com.google.gson.annotations.SerializedName


data class BaseResponse<T>(
    @SerializedName("code")
    val code:Int=0,
    @SerializedName("message")
    val message:String="",
    @SerializedName("data")
    val data:T,
    @SerializedName("status_code")
    val status_code:Int
    )
