package com.example.owentime.bean
import android.view.View
import coil.load
import com.drake.brv.BindingAdapter
import com.drake.brv.item.ItemBind
import com.example.owentime.databinding.ItemProductBinding

import com.google.gson.annotations.SerializedName
import com.tencent.mmkv.MMKV



data class HomeModel(
    @SerializedName("banner")
    val banner: List<Banner>? = listOf(),
    @SerializedName("product")
    val product: List<Product>? = listOf(),
    @SerializedName("studying")
    val studying: List<Studying>? = listOf(Studying()),//记得改回来，先前没有数据，所以用了个string
    @SerializedName("user")
//    @Serializable(with = UserListSerializer::class)
    val user: List<User>? = listOf(User())
)

data class Banner(
    @SerializedName("activity_links")
    val activityLinks: String? = "",
    @SerializedName("id")
    val id: Int? = 0,
    @SerializedName("jump_type")
    val jumpType: Int? = 0,
    @SerializedName("title")
    val title: String? = "",
    @SerializedName("url")
    val url: String? = ""
)

data class Product(
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

data class User(
    @SerializedName("code")
    val code: String? = "",
    @SerializedName("id")
    val id: Int? = 0,
    @SerializedName("phone")
    val phone: String? = "",
    @SerializedName("photo")
    val photo: String? = "",
    @SerializedName("sex")
    val sex: Int? = 0
)

class Studying{}