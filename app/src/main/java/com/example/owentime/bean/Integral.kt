package com.example.owentime.bean
import kotlinx.serialization.Serializable

import kotlinx.serialization.SerialName


@Serializable
data class Integral(
    @SerialName("coinCount")
    var coinCount: Int = 0,
    @SerialName("date")
    var date: Long = 0,
    @SerialName("desc")
    var desc: String = "",
    @SerialName("id")
    var id: Int = 0,
    @SerialName("reason")
    var reason: String = "",
    @SerialName("type")
    var type: Int = 0,
    @SerialName("userId")
    var userId: Int = 0,
    @SerialName("userName")
    var userName: String = ""
)