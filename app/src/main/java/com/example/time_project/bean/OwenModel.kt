package com.example.time_project.bean
import android.view.View
import coil.load
import com.drake.brv.BindingAdapter
import com.drake.brv.item.ItemBind
import com.example.time_project.databinding.ItemProductBinding
import com.google.gson.annotations.SerializedName
import com.tencent.mmkv.MMKV

//订单确认
//start————————————————————————————————————————————————————————
data class ConfirmOrderModel(
    @SerializedName("address")
    val address: Address? = Address(),
    @SerializedName("coupon")
    val coupon: Coupon? = Coupon(),
    @SerializedName("freight")
    val freight: Int? = 0,
    @SerializedName("price_actual")
    val priceActual: Double? = 0.0,
    @SerializedName("price_show")
    val priceShow: Double? = 0.0,
    @SerializedName("product")
    val product: Product01? = Product01()
)

data class Address(
    @SerializedName("address")
    val address: String? = "",
    @SerializedName("address_id")
    val addressId: Int? = 0,
    @SerializedName("area")
    val area: String? = "",
    @SerializedName("city")
    val city: String? = "",
    @SerializedName("is_default")
    val isDefault: Int? = 0,
    @SerializedName("name")
    val name: String? = "",
    @SerializedName("phone")
    val phone: String? = "",
    @SerializedName("province")
    val province: String? = "",
    @SerializedName("user_id")
    val userId: Int? = 0
)

data class Coupon(
    @SerializedName("coupon_code")
    val couponCode: String? = "",
    @SerializedName("coupon_price")
    val couponPrice: Int? = 0,
    @SerializedName("num")
    val num: Int? = 0
)

data class Product01(
    @SerializedName("code")
    val code: String? = "",
    @SerializedName("gift")
    val gift: List<Gift>? = listOf(),
    @SerializedName("img_show")
    val imgShow: String? = "",
    @SerializedName("name")
    val name: String? = "",
    @SerializedName("price_actual")
    val priceActual: String? = "",
    @SerializedName("product_id")
    val productId: Int? = 0,
    @SerializedName("sku_id")
    val skuId: Int? = 0,
    @SerializedName("sku_name")
    val skuName: String? = ""
)

data class Gift(
    @SerializedName("gift_id")
    val giftId: Int? = 0,
    @SerializedName("id")
    val id: Int? = 0,
    @SerializedName("img_head")
    val imgHead: String? = "",
    @SerializedName("name")
    val name: String? = "",
    @SerializedName("num")
    val num: Int? = 0,
    @SerializedName("price")
    val price: String? = "",
    @SerializedName("sku_id")
    val skuId: Int? = 0
)
//end————————————————————————————————————————————————————————

//下单start----------------------------------------------------------
data class OrderSn(
    @SerializedName("order_sn")
    val orderSn: String? = ""
)
//已购start-------------------------------------------------------------
data class AlreadyBuyModel(
    @SerializedName("purchased")
    val purchased: List<Purchased>? = listOf(),
    @SerializedName("recommend")
    val recommend: List<Recommend>? = listOf()
)

data class Purchased(
    @SerializedName("id")
    val id: Int? = 0,
    @SerializedName("name")
    val name: String? = "",
    @SerializedName("product")
    val product: List<Product02>? = listOf()
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
    val userCount: Int? = 0
):ItemBind {
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
    val token= MMKV.defaultMMKV().decodeString("token")
    override fun onBind(holder: BindingAdapter.BindingViewHolder) {
        val binding= ItemProductBinding.bind(holder.itemView)
        binding.ivProduct.load(imgHead)
        binding.tvProductTitle.text=name
        binding.tvProductDesc.text=introduction
        binding.tvProductPrice02.visibility=View.GONE
        binding.tvProductPtnum.visibility=View.GONE
        binding.btnGoto.visibility=View.INVISIBLE

    }
}
//已购end------------------------------------------------------
