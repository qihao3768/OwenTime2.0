package com.example.owentime.bean
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Register(
    @SerializedName("admin")
    var admin: Boolean = false,
    @SerializedName("chapterTops")
    var chapterTops: List<String> = listOf(),
    @SerializedName("coinCount")
    var coinCount: Int = 0,
    @SerializedName("collectIds")
    var collectIds: List<String> = listOf(),
    @SerializedName("email")
    var email: String = "",
    @SerializedName("icon")
    var icon: String = "",
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("nickname")
    var nickname: String = "",
    @SerializedName("password")
    var password: String = "",
    @SerializedName("publicName")
    var publicName: String = "",
    @SerializedName("token")
    var token: String = "",
    @SerializedName("type")
    var type: Int = 0,
    @SerializedName("username")
    var username: String = ""
):Parcelable


data class SmsModel(
    @SerializedName("expired_at")
    val expiredAt: String? = "",
    @SerializedName("key_code")
    val keyCode: String? = ""
)
