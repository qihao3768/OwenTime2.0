package com.example.time_project.bean
import com.google.gson.annotations.SerializedName


data class WeiXinPay(
    @SerializedName("appid")
    val appid: String? = "",
    @SerializedName("noncestr")
    val noncestr: String? = "",
    @SerializedName("package")
    val packageX: String? = "",
    @SerializedName("partnerid")
    val partnerid: String? = "",
    @SerializedName("prepayid")
    val prepayid: String? = "",
    @SerializedName("sign")
    val sign: String? = "",
    @SerializedName("timestamp")
    val timestamp: String? = ""
)