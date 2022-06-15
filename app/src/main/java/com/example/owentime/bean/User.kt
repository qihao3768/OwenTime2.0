package com.example.owentime.bean
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import com.squareup.moshi.JsonClass

import com.squareup.moshi.Json

//获取短信
@JsonClass(generateAdapter = true)
data class SmsModel(
    @Json(name = "expired_at")
    val expiredAt: String? = "",
    @Json(name = "key_code")
    var keyCode: String? = ""
)
//短信登录
@JsonClass(generateAdapter = true)
data class LoginModel(
    @Json(name = "access_token")
    val accessToken: String? = "",
    @Json(name = "expires_in")
    val expiresIn: Int? = 0,
    @Json(name = "token_type")
    val tokenType: String? = "",
    @Json(name = "info_flag")
    val infoFlag:Int?=0
)

//头像
@JsonClass(generateAdapter = true)
data class PhotoModel(
    @Json(name = "photo")
    val photo: String? = ""
)

//用户信息
@JsonClass(generateAdapter = true)
data class UserModel(
    @Json(name = "code")
    val code: String? = "",
    @Json(name = "id")
    val id: Int? = 0,
    @Json(name = "phone")
    val phone: String? = "",
    @Json(name = "photo")
    val photo: String? = "",
    @Json(name = "sex")
    val sex: String? = ""
)


