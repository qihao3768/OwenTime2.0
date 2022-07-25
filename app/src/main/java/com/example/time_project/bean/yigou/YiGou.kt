package com.example.time_project.bean.yigou
import android.view.View
import coil.load
import com.drake.brv.BindingAdapter
import com.drake.brv.item.ItemBind
import com.example.time_project.databinding.ItemProduct2Binding

import com.example.time_project.databinding.ItemProductBinding
import com.google.gson.annotations.SerializedName
import com.tencent.mmkv.MMKV

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


//已购start-------------------------------------------------------------
data class AlreadyBuyModel(
    @SerializedName("purchased")
    val purchased: List<Purchased>? = listOf(),
    @SerializedName("recommend")
    val recommend: RecommendData ?= RecommendData(),
    @SerializedName("page_count")
    val pageCount: Int? = 0
//    @SerializedName("recommend")
//    val recommend: List<Recommend>? = listOf()
)

data class Purchased(
    @SerializedName("id")
    val id: Int? = 0,
    @SerializedName("name")
    val name: String? = "",
    @SerializedName("product")
    val product: List<Product02>? = listOf()
)

data class RecommendData(
    @SerializedName("data")
    val data: List<Recommend>? = listOf()
)
data class Recommend(
    @SerializedName("code")
    val code: String? = "",
    @SerializedName("id")
    val id: Int? = 0,
    @SerializedName("img_head")
    val imgHead: String? = "",
    @SerializedName("introduction")
    val introduction: String? = "",
    @SerializedName("name")
    val name: String? = "",
    @SerializedName("price_actual")
    val priceActual: String? = "",
    @SerializedName("price_show")
    val priceShow: String? = "",
    @SerializedName("user_count")
    val userCount: Int? = 0,
    @SerializedName("type")
    val type: Int? = 0

): ItemBind {
    val token = MMKV.defaultMMKV().decodeString("token")
    override fun onBind(holder: BindingAdapter.BindingViewHolder) {
        val binding = ItemProductBinding.bind(holder.itemView)
        binding.ivProduct.load(imgHead)
        binding.tvProductTitle.text = name
        binding.tvProductDesc.text = introduction
        binding.tvProductPrice02.text = priceShow
        binding.tvProductPtnum.text = userCount.toString().plus("人购买")
        binding.btnGoto.visibility = View.GONE

    }
}

data class Product02(
    @SerializedName("code")
    val code: String? = "",
    @SerializedName("id")
    val id: Int? = 0,
    @SerializedName("img_head")
    val imgHead: String? = "",
    @SerializedName("img_sku")
    val imgSku: String? = "",
    @SerializedName("introduction")
    val introduction: String? = "",
    @SerializedName("name")
    val name: String? = "",
    @SerializedName("sku_id")
    val skuId: Int? = 0,
    @SerializedName("sku_name")
    val skuName: String? = "",
    @SerializedName("type")
    val type: Int? = 0
): ItemBind {
//    val token= MMKV.defaultMMKV().decodeString("token")
    override fun onBind(holder: BindingAdapter.BindingViewHolder) {
        val binding= ItemProduct2Binding.bind(holder.itemView)
        binding.ivProduct.load(imgHead)
        binding.tvProductTitle.text=name
        binding.tvProductDesc.text=introduction
        binding.tvProductPrice02.visibility= View.GONE
        binding.tvProductPtnum.visibility= View.GONE
        binding.btnGoto.visibility= View.VISIBLE

    }
}

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