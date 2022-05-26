package com.example.owentime.bean
import android.os.Parcelable

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.ArrayList

//商品列表部分---------------------------------------------
@Serializable
data class GoodList(
    @SerialName("goods")
    var goods:List<GoodsBean> = listOf(),
    @SerialName("page_count")
var pageCount:Int=1
)
@Serializable
data class GoodsBean(
    @SerialName("buy_count")
    var buyCount: Int = 0,
    @SerialName("display_image")
    var displayImage: String ?= "",
    @SerialName("goods_id")
    var goodsId: Int = 0,
    @SerialName("goods_type")
    var goodsType: Int = 0,
    @SerialName("group_goods_id")
    var groupGoodsId: Int = 0,
    @SerialName("group_price")
    var groupPrice: String ?= "",
    @SerialName("name")
    var name: String ?= ""
)
//商品列表部分---------------------------------------------


//商品详情部分---------------------------------------------
//@Serializable
//data class GoodsDetail(
//    @SerialName("ageArr")
//    var ageArr: List<String> = listOf(),
//    @SerialName("group")
//    var group: MutableList<Group> ?= mutableListOf(),
//    @SerialName("option")
//    var option: ArrayList<Option> = arrayListOf(),
//    @SerialName("res")
//    var res: Res ?= Res(),
//    @SerialName("selectCourse")
//    var selectCourse: List<SelectCourse> = listOf(),
//    @SerialName("expRes")
//    var expRes:ExpRes?=ExpRes(),
//    @SerialName("buy_status")
//    var buyStatus:Int=-1
//
//)
//@Serializable
//data class Group(
//    @SerialName("close_time")
//    var closeTime: String = "",
//    @SerialName("created_at")
//    var createdAt: String = "",
//    @SerialName("group_goods_id")
//    var groupGoodsId: Int = 0,
//    @SerialName("group_population")
//    var groupPopulation: Int = 0,
//    @SerialName("group_status")
//    var groupStatus: Int = 0,
//    @SerialName("hour")
//    var hour: List<String> = listOf(),
//    @SerialName("id")
//    var id: Int = 0,
//    @SerialName("last_population")
//    var lastPopulation: Int = 0,
//    @SerialName("user")
//    var user: User ?= User()
//)

@Serializable
data class Option(
    @SerialName("group_price")
    var groupPrice: String = "",
    @SerialName("id")
    var id: Int = 0,
    @SerialName("is_default")
    var isDefault: Int = 0,
    @SerialName("month")
    var month: Int = 0,
    @SerialName("price")
    var price: String = "",
    @SerialName("title")
    var title: String = ""
)

@Serializable
data class Res(
    @SerialName("actual_price")
    var actualPrice: String = "",
    @SerialName("detailed_inf")
    var detailedInf: String = "",
    @SerialName("goods_id")
    var goodsId: Int = 0,
    @SerialName("goods_type")
    var goodsType: Int = 0,
    @SerialName("group_goods_id")
    var groupGoodsId: Int = 0,
    @SerialName("group_price")
    var groupPrice: String = "",
    @SerialName("head_image")
    var headImage: MutableList<String> = mutableListOf(),
    @SerialName("title")
    var title: String = ""
)

@Serializable
data class SelectCourse(
    @SerialName("buy_flag")
    var buyFlag: Int = 0,
    @SerialName("early_box_id")
    var earlyBoxId: Int = 0,
    @SerialName("selected_flag")
    var selectedFlag: Int = 0,
    @SerialName("title")
    var title: String = "",
    var bo:Boolean=false
)

//@Serializable
//data class User(
//    @SerialName("id")
//    var id: Int = 0,
//    @SerialName("name")
//    var name: String ?= "",
//    @SerialName("photo")
//    var photo: String ?= ""
//)

//@Serializable
//data class ExpRes(
//    @SerialName("buy_flag")
//    var buyFlag:Int=0,
//    @SerialName("msg")
//    var msg:String?=""
//)
//商品详情部分---------------------------------------------