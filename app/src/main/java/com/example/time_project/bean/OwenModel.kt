package com.example.time_project.bean
import com.google.gson.annotations.SerializedName

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