package com.example.time_project.ui

import android.content.Intent
import android.view.Gravity
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.time_project.*
import com.example.time_project.base.BaseActivity
import com.example.time_project.base.BasePopWindow
import com.example.time_project.bean.ConfirmOrderRequestBody
import com.example.time_project.bean.UpOrderDetailRequestBody
import com.example.time_project.bean.UpOrderRequestBody
import com.example.time_project.databinding.ActivityUpOrderBinding
import com.example.time_project.databinding.SelectbuywayLayout2Binding
import com.example.time_project.util.IntentExtra.Companion.icode
import com.example.time_project.util.IntentExtra.Companion.icoupon
import com.example.time_project.util.IntentExtra.Companion.inum
import com.example.time_project.util.IntentExtra.Companion.iproductId
import com.example.time_project.util.IntentExtra.Companion.isku

import com.example.time_project.util.IntentExtraInt
import com.example.time_project.util.IntentExtraString
import com.example.time_project.vm.OwenViewModel
import com.gyf.immersionbar.ktx.immersionBar
import razerdp.util.animation.AnimationHelper
import razerdp.util.animation.TranslationConfig

class UpOrderActivity : BaseActivity(R.layout.activity_up_order) {
    private val mBinding by viewBinding (ActivityUpOrderBinding::bind)
    private var payDialog = BasePopWindow(this)
    private lateinit var payBinding: SelectbuywayLayout2Binding
    private val viewModel by viewModels<OwenViewModel>()
    private var body:UpOrderRequestBody?=null
    private var mOrderSn:String=""//订单号
    private var mPayWay:Int=1//支付方式，1 微信 2 支付宝

    companion object IntentOptions{
        var Intent.iprovince by IntentExtraString("province")//商品代码
        var Intent.icity by IntentExtraString("city")//skuid
        var Intent.iarea by IntentExtraString("area")//购买数量
        var Intent.iname by IntentExtraString("name")//收货人
        var Intent.iphone by IntentExtraString("phone")//收货人电话
        var Intent.iaddress by IntentExtraString("address")//收货地址
        var Intent.iid by IntentExtraInt("id")//优惠券id
        var Intent.iflag by IntentExtraString("change")//优惠券id
    }

    override fun initData() {
        immersionBar {
            keyboardEnable(true)
            statusBarDarkFont(true)
                .titleBar(mBinding.titleOrder)

            val view=layoutInflater.inflate(R.layout.selectbuyway_layout2,null)
            payBinding= SelectbuywayLayout2Binding.bind(view)
        }
        mBinding.layoutAddress01.setOnClickListener {
            start(this@UpOrderActivity,AddressActivity().javaClass,false)
        }
        mBinding.btnOrdersubmit.setOnClickListener {
            //如果订单号不为空，说明在当前页面已经执行了一次下单操作，并且已经生成了订单，就不能再当前页面重复下单了
            if (mOrderSn.isEmpty()){
                //下单成功之后弹出支付方式
                body?.run {
                    viewModel.upOrder(this).observe(this@UpOrderActivity, Observer {
                        it?.run {
                            when(code){
                                1000->{
                                    data?.run {
                                        mOrderSn=orderSn.toString()
                                        showPay()
                                    }
                                }else->{
                                toast(message.toString())
                            }
                            }
                        }
                    })
                }
            }else{
                showPay()
            }
        }
//订单确认
        viewModel.confirmPage(initParams()).observe(this, Observer {
            it?.run {

                mBinding.orderPrice.text="￥".plus(priceShow)
                mBinding.tvOrdernum.text="x".plus(intent.inum)
                mBinding.tvOrderjianshu.text="共".plus(intent.inum).plus("件")
                address?.run {
                    mBinding.layoutAddress01.visibility= View.GONE
                    mBinding.groupAddress.visibility=View.VISIBLE
                    mBinding.addressName.text=name?:""
                    mBinding.addressPhone.text=phone?:""
                    val maddress=(province?:"").plus(city?:"").plus(area?:"").plus(address?:"")
                    mBinding.tvAddressDetail.text=maddress

                    intent.iprovince=province
                    intent.icity=city
                    intent.iarea=area
                    intent.iaddress=address
                    intent.iname=name
                    intent.iphone=phone
                    intent.iid=addressId?:-1
                }
                product?.run {
                    mBinding.tvOrdertitle.text=name?:""
                    mBinding.ivOrderpic.load(imgShow?:"")
                    mBinding.ivOrderpic.load(imgShow?:"")
                    mBinding.tvPrice.text="￥".plus(priceActual?:"")
                }
                mBinding.tvTotalPrice.text=(priceActual?:0.00).toString()

                val detailRequestBody=UpOrderDetailRequestBody(product_id=intent.iproductId.toString(), sku_id = intent.isku?:"",product_quantity=intent.inum?:"")

                body= UpOrderRequestBody(order_type = "0",total_amount=priceShow.toString(),
                    pay_amount=priceActual.toString(),freight_amount=freight.toString(), note = "",
                    coupon_code = intent.icoupon?:"", coupon_amount = "0", detail = listOf(detailRequestBody), address_id = intent.iid.toString())//下单时携带的body

            }

        })
        //返回上一页
        mBinding.titleOrder.leftView.fastClick {
            finish()
        }
        //去修改地址
        mBinding.layoutAddress02.fastClick {
            intent.iflag="change"
            start(this@UpOrderActivity,AddressActivity().javaClass,intent)
        }
    }

    /***
     * 支付弹框
     */
    private fun showPay(){
        payDialog.contentView=payBinding.root
        payBinding.payClose.setOnClickListener {
            payDialog.dismiss()
        }
        //微信支付
        payBinding.wxBuys.fastClick {
            mPayWay=1
            payBinding.wxCheck.isSelected=true
            payBinding.zfbCheck.isSelected=false
        }
        payBinding.wxCheck.fastClick {
            mPayWay=1
            payBinding.wxCheck.isSelected=true
            payBinding.zfbCheck.isSelected=false
        }
        //支付宝支付
        payBinding.zfbBuy.fastClick {
            mPayWay=2
            payBinding.wxCheck.isSelected=false
            payBinding.zfbCheck.isSelected=true
        }
        payBinding.zfbCheck.fastClick {
            mPayWay=2
            payBinding.wxCheck.isSelected=false
            payBinding.zfbCheck.isSelected=true
        }
        payBinding.orderPopnumber.text=mBinding.tvTotalPrice.text
        payDialog.setOutSideDismiss(true).setOutSideTouchable(true)
            .setPopupGravity(Gravity.BOTTOM)
            .setShowAnimation(
                AnimationHelper.asAnimation().withTranslation(TranslationConfig.FROM_BOTTOM)
                    .toShow()
            )
            .setDismissAnimation(
                AnimationHelper.asAnimation().withTranslation(TranslationConfig.TO_BOTTOM)
                    .toDismiss()
            )
            .showPopupWindow()
    }



    private fun initParams():HashMap<String,Any>{
        return ConfirmOrderRequestBody(intent.icode?:"",intent.isku?:"",
            intent.inum?:"",intent.icoupon?:""
        ).toMap()
    }
}