package com.example.owentime.bean

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Page<T>(
    @SerialName("curPage")
    var curPage:Int = 1,
    @SerialName("datas")
    var datas:List<T> = listOf(),
    @SerialName("offset")
    var offset:Int = 0,
    @SerialName("over")
    var over:Boolean = false,
    @SerialName("pageCount")
    var pageCount:Int = 1,
    @SerialName("size")
    var size:Int = 0,
    @SerialName("total")
    var total:Int = 0
)