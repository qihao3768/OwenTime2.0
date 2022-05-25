package com.example.owentime.bean
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

import kotlinx.serialization.SerialName


@Serializable
@Parcelize
data class Register(
    @SerialName("admin")
    var admin: Boolean = false,
    @SerialName("chapterTops")
    var chapterTops: List<String> = listOf(),
    @SerialName("coinCount")
    var coinCount: Int = 0,
    @SerialName("collectIds")
    var collectIds: List<String> = listOf(),
    @SerialName("email")
    var email: String = "",
    @SerialName("icon")
    var icon: String = "",
    @SerialName("id")
    var id: Int = 0,
    @SerialName("nickname")
    var nickname: String = "",
    @SerialName("password")
    var password: String = "",
    @SerialName("publicName")
    var publicName: String = "",
    @SerialName("token")
    var token: String = "",
    @SerialName("type")
    var type: Int = 0,
    @SerialName("username")
    var username: String = ""
):Parcelable