package com.example.time_project.bean.order

import android.view.View
import com.drake.brv.BindingAdapter
import com.drake.brv.item.ItemBind
import com.example.time_project.R
import com.example.time_project.databinding.ItemOrderBinding
import com.example.time_project.databinding.LayoutWorksBinding
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

data class OrderModel(val pic:String,val title:String,val price:String,val state:String): ItemBind {
    override fun onBind(holder: BindingAdapter.BindingViewHolder) {
        val binding= ItemOrderBinding.bind(holder.itemView)
        binding.orderTitle.text=title
//        binding.orderPic.load(pic)
        binding.orderPrice.text=price
        binding.orderState.text=state


    }
}

//订单列表
data class OrderListModel(
    @SerializedName("data")
    val orderlist: List<OrderListData> = emptyList(),
    @SerializedName("page_count")
    val pageCount: Int? = 0
)

data class OrderListData(
    @SerializedName("close_time")
    val closeTime: String? = "",
    @SerializedName("delivery_company")
    val deliveryCompany: String? = "",
    @SerializedName("delivery_sn")
    val deliverySn: String? = "",
    @SerializedName("detail")
    val detail: MutableList<OrderListDetail>? = mutableListOf(),
    @SerializedName("hour")
    val hour: MutableList<String?>? = mutableListOf(),
    @SerializedName("order_sn")
    val orderSn: String? = "",
    @SerializedName("order_status")
    val orderStatus: Int? = 0,
    @SerializedName("order_type")
    val orderType: Int? = 0,
    @SerializedName("pay_amount")
    val payAmount: Double? = 0.0
):ItemBind{
    override fun onBind(holder: BindingAdapter.BindingViewHolder) {
        val binding= ItemOrderBinding.bind(holder.itemView)
        detail?.run {
            binding.orderTitle.text= this[0].name
            binding.orderState.text=getState(orderStatus?:-1,binding)
            binding.orderPrice.text="实付款".plus("￥ ").plus(payAmount)

            binding.orderSn.text=orderSn
        }

    }

    /***
     * 转换订单状态
     */
    private fun getState(state: Int,binding:ItemOrderBinding):String{
        return when(state){
            0->{
                "待付款"
            }
            1->{
//                binding.layoutNumber.visibility=View.GONE
//                binding.orderState.setTextColor(R.color.A8A8A8)
                binding.btnPay.visibility=View.GONE

                "待发货"
            }
            2->{
                "已发货"
            }
            3->{
                "已完成"
            }
            4->{
                "已关闭"
            }
            5->{
                "已取消"
            }
            else -> {
                "状态未知"
            }
        }
    }


}

data class OrderListDetail(
    @SerializedName("name")
    val name: String? = "",
    @SerializedName("num")
    val num: Int? = 0,
    @SerializedName("price")
    val price: String? = "",
    @SerializedName("product_id")
    val productId: Int? = 0,
    @SerializedName("product_img")
    val productImg: String? = "",
    @SerializedName("sku_id")
    val skuId: Int? = 0,
    @SerializedName("sku_img")
    val skuImg: String? = "",
    @SerializedName("sku_name")
    val skuName: String? = ""
)

