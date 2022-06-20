package com.example.owentime.bean

data class AddressRequestBody(
    val name: String,
    val phone: String,
    val province: String,
    val city: String,
    val area: String,
    val address: String,
    val is_default: String


){
    fun toMap(): HashMap<String,Any> {
        return hashMapOf(Pair<String,String>("name",name),
            Pair<String,String>("phone",phone),
            Pair<String,String>("province",province),
            Pair<String,String>("city",city),
            Pair<String,String>("area",area),
            Pair<String,String>("address",address),
            Pair<String,String>("is_default",is_default)
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
){
    fun toMap(): HashMap<String,Any> {
        return hashMapOf(Pair<String,String>("code",code),
            Pair<String,String>("sku",sku),
            Pair<String,String>("num",num),
            Pair<String,String>("coupon",coupon),
        )
    }
}

