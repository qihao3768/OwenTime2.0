package com.example.owentime.bean
import android.view.View
import coil.load
import com.drake.brv.BindingAdapter
import com.drake.brv.item.ItemBind
import com.example.owentime.databinding.ItemProductBinding

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.tencent.mmkv.MMKV


@JsonClass(generateAdapter = true)
data class HomeModel(
    @Json(name="banner")
    val banner: List<Banner>? = listOf(),
    @Json(name="product")
    val product: List<Product>? = listOf(),
    @Json(name="studying")
    val studying: List<Studying>? = listOf(Studying()),//记得改回来，先前没有数据，所以用了个string
    @Json(name="user")
//    @Serializable(with = UserListSerializer::class)
    val user: List<User>? = listOf(User())
)
@JsonClass(generateAdapter = true)
data class Banner(
    @Json(name="activity_links")
    val activityLinks: String? = "",
    @Json(name = "id")
    val id: Int? = 0,
    @Json(name="jump_type")
    val jumpType: Int? = 0,
    @Json(name="title")
    val title: String? = "",
    @Json(name="url")
    val url: String? = ""
)
@JsonClass(generateAdapter = true)
data class Product(
    @Json(name="code")
    val code: String? = "",
    @Json(name="id")
    val id: Int? = 0,
    @Json(name="img_head")
    val imgHead: String? = "",
    @Json(name="introduction")
    val introduction: String? = "",
    @Json(name="name")
    val name: String? = "",
    @Json(name="price_actual")
    val priceActual: String? = "",
    @Json(name="price_show")
    val priceShow: String? = "",
    @Json(name="user_count")
    val userCount: Int? = 0
):ItemBind{
    val islogin= MMKV.defaultMMKV().decodeBool("islogin",false)
    override fun onBind(holder: BindingAdapter.BindingViewHolder) {
        val binding= ItemProductBinding.bind(holder.itemView)
        binding.ivProduct.load(imgHead)
        binding.tvProductTitle.text=name
        binding.tvProductDesc.text=introduction
        binding.tvProductPrice02.text=priceShow
        binding.tvProductPtnum.text=userCount.toString().plus("人购买")
        binding.btnGoto.visibility=if (islogin){
            View.VISIBLE

        }else{
            View.INVISIBLE
        }

    }
}
@JsonClass(generateAdapter = true)
data class User(
    @Json(name="code")
    val code: String? = "",
    @Json(name="id")
    val id: Int? = 0,
    @Json(name="phone")
    val phone: String? = "",
    @Json(name="photo")
    val photo: String? = "",
    @Json(name="sex")
    val sex: Int? = 0
)
@JsonClass(generateAdapter = true)
class Studying{}