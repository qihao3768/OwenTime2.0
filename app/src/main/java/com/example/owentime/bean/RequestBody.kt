package com.example.owentime.bean

data class AddressRequestBody(
    val name: String,
    val phone: String,
    val province: String,
    val city: String,
    val area: String,
    val address: String,
    val is_default: String


) {
    fun toMap(): HashMap<String, Any> {
        return hashMapOf(
            Pair<String, String>("name", name),
            Pair<String, String>("phone", phone),
            Pair<String, String>("province", province),
            Pair<String, String>("city", city),
            Pair<String, String>("area", area),
            Pair<String, String>("address", address),
            Pair<String, String>("is_default", is_default)
        )
    }
}

/***
 * 确认订单的请求参数
 * code 商品代码
 * sku 商品的skuid
 * num 购买数量
 * coupon 优惠券代码
 */
data class ConfirmOrderRequestBody(
    val code: String,
    val sku: String,
    val num: String,
    val coupon: String,
) {
    fun toMap(): HashMap<String, Any> {
        return hashMapOf(
            Pair<String, String>("code", code),
            Pair<String, String>("sku", sku),
            Pair<String, String>("num", num),
            Pair<String, String>("coupon", coupon),
        )
    }
}

data class ChangeAddressRequestBody(
    val name: String,
    val phone: String,
    val province: String,
    val city: String,
    val area: String,
    val address: String,
    val is_default: String,
    val id:String,
    val zip_code:String
) {
    fun toMap(): HashMap<String, Any> {
        return hashMapOf(
            Pair<String, String>("name", name),
            Pair<String, String>("phone", phone),
            Pair<String, String>("province", province),
            Pair<String, String>("city", city),
            Pair<String, String>("area", area),
            Pair<String, String>("address", address),
            Pair<String, String>("is_default", is_default),
            Pair<String, String>("id", id),
            Pair<String, String>("zip_code", zip_code),
        )
    }
}
 class UpOrderRequestBody{

     var order_type: String=""
     var total_amount:String=""
     var pay_amount:String=""
     var freight_amount:String=""
     var address_id:String=""
     var coupon_code:String=""
     var coupon_amount:String=""
     var note:String=""
     var detail:UpOrderDetailRequestBody=UpOrderDetailRequestBody()
//    fun toMap(): HashMap<String, Any> {
//        return hashMapOf(
//            Pair<String, String>("order_type", order_type),
//            Pair<String, String>("total_amount", total_amount),
//            Pair<String, String>("pay_amount", pay_amount),
//            Pair<String, String>("freight_amount", freight_amount),
//            Pair<String, String>("address_id", address_id),
//            Pair<String, String>("coupon_code", coupon_code),
//            Pair<String, String>("coupon_amount", coupon_amount),
//            Pair<String, String>("note", note),
//            Pair<String, UpOrderDetailRequestBody>("detail", detail),
//        )
//    }
}

class UpOrderDetailRequestBody{
    var product_id:String=""
    var sku_id:String=""
    var product_quantity:String=""
}

