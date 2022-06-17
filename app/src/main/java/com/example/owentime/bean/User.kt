package com.example.owentime.bean
import android.os.Parcelable
import com.google.gson.annotations.SerializedName


//获取短信
//@SerializedNameClass(generateAdapter = true)
data class SmsModel(
    @SerializedName("expired_at")
    val expiredAt: String? = "",
    @SerializedName("key_code")
    var keyCode: String? = ""
)
//短信登录
//@SerializedNameClass(generateAdapter = true)
data class LoginModel(
    @SerializedName("access_token")
    val accessToken: String? = "",
    @SerializedName("expires_in")
    val expiresIn: Int? = 0,
    @SerializedName("token_type")
    val tokenType: String? = "",
    @SerializedName("info_flag")
    val infoFlag:Int?=0
)

//头像
//@SerializedNameClass(generateAdapter = true)
data class PhotoModel(
    @SerializedName("photo")
    val photo: String? = ""
)

//用户信息
//@SerializedNameClass(generateAdapter = true)
data class UserModel(
    @SerializedName("code")
    val code: String? = "",
    @SerializedName("id")
    val id: Int? = 0,
    @SerializedName("phone")
    val phone: String? = "",
    @SerializedName("photo")
    val photo: String? = "",
    @SerializedName("sex")
    val sex: String? = ""
)


