package com.example.time_project.bean
import com.google.gson.annotations.SerializedName

data class YiGouPage(
    @SerializedName("data")
    val data: Data? = Data(),
    @SerializedName("page_count")
    val pageCount: Int? = 0
)

data class Data(
    @SerializedName("product")
    val product: List<Product02?>? = listOf(),
    @SerializedName("recommend")
    val recommend: List<Recommend?>? = listOf()
)

//data class Product(
//    @SerializedName("code")
//    val code: String? = "",
//    @SerializedName("id")
//    val id: Int? = 0,
//    @SerializedName("img_head")
//    val imgHead: String? = "",
//    @SerializedName("img_sku")
//    val imgSku: String? = "",
//    @SerializedName("introduction")
//    val introduction: String? = "",
//    @SerializedName("name")
//    val name: String? = "",
//    @SerializedName("sku_id")
//    val skuId: Int? = 0,
//    @SerializedName("sku_name")
//    val skuName: String? = "",
//    @SerializedName("type")
//    val type: Int? = 0
//)
//
//data class Recommend(
//    @SerializedName("code")
//    val code: String? = "",
//    @SerializedName("id")
//    val id: Int? = 0,
//    @SerializedName("img_head")
//    val imgHead: String? = "",
//    @SerializedName("introduction")
//    val introduction: String? = "",
//    @SerializedName("name")
//    val name: String? = "",
//    @SerializedName("price_actual")
//    val priceActual: String? = "",
//    @SerializedName("price_show")
//    val priceShow: String? = "",
//    @SerializedName("type")
//    val type: Int? = 0,
//    @SerializedName("user_count")
//    val userCount: Int? = 0
//)