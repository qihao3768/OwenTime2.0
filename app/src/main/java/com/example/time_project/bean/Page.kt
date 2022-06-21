package com.example.time_project.bean

import com.google.gson.annotations.SerializedName


data class Page<T>(
    @SerializedName("curPage")
    var curPage:Int = 1,
    @SerializedName("datas")
    var datas:List<T> = listOf(),
    @SerializedName("offset")
    var offset:Int = 0,
    @SerializedName("over")
    var over:Boolean = false,
    @SerializedName("pageCount")
    var pageCount:Int = 1,
    @SerializedName("size")
    var size:Int = 0,
    @SerializedName("total")
    var total:Int = 0
)