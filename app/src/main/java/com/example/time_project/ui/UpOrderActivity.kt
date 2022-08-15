package com.example.time_project.ui

import android.content.Intent
import android.location.Address
import android.os.Handler
import android.os.Message
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.lifecycle.Observer
import by.kirich1409.viewbindingdelegate.viewBinding
import com.alipay.sdk.app.PayTask
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup
import com.example.time_project.*
import com.example.time_project.base.BaseActivity
import com.example.time_project.base.BasePopWindow
import com.example.time_project.bean.*
import com.example.time_project.bean.order.Gift
import com.example.time_project.bean.order.Product01

import com.example.time_project.databinding.ActivityUpOrderBinding
import com.example.time_project.databinding.LayoutNoteBinding
import com.example.time_project.databinding.LayoutSpecificationsBinding
import com.example.time_project.databinding.SelectbuywayLayout2Binding
import com.example.time_project.util.IntentExtra.Companion.icode
import com.example.time_project.util.IntentExtra.Companion.icoupon
import com.example.time_project.util.IntentExtra.Companion.inum
import com.example.time_project.util.IntentExtra.Companion.iproductId
import com.example.time_project.util.IntentExtra.Companion.isku
import com.example.time_project.util.IntentExtra.Companion.iskuName

import com.example.time_project.util.IntentExtraInt
import com.example.time_project.util.IntentExtraString
import com.example.time_project.vm.OwenViewModel
import com.gyf.immersionbar.ktx.immersionBar
import com.jeremyliao.liveeventbus.LiveEventBus
import com.tencent.mm.opensdk.modelpay.PayReq
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import razerdp.util.animation.AnimationHelper
import razerdp.util.animation.TranslationConfig
import kotlin.text.Typography.times

class UpOrderActivity : BaseActivity(R.layout.activity_up_order) {
    private val mBinding by viewBinding (ActivityUpOrderBinding::bind)
    private var payDialog = BasePopWindow(this)
    private lateinit var payBinding: SelectbuywayLayout2Binding
    private val viewModel by viewModels<OwenViewModel>()
    private var body:UpOrderRequestBody?=null
    private var mOrderSn:String=""//订单号
    private var mPayWay:Int=1//支付方式，1 微信 2 支付宝
    private var mTitle:String=""

    private val SDK_PAY_FLAG:Int=1001

    private val mHandler: Handler= Handler(Handler.Callback() {
        when (it.what) {
            SDK_PAY_FLAG -> {
                val payResult = PayResult(it.obj as MutableMap<String, String>)
                //同步获取结果
                val resultInfo = payResult.result
//                Log.i("Pay", "Pay:$resultInfo")
                val resultStatus = payResult.resultStatus
                val memo = payResult.memo

                // 判断resultStatus 为9000则代表支付成功
                if (TextUtils.equals(resultStatus, "9000")) {
                    toast("支付成功")
                    start(this@UpOrderActivity,PaySuccessActivity().javaClass,true)


                } else {
//                    LiveEventBus.get<Any>("dfk").post(1000)
//                    val intent = Intent()
//                    intent.putExtra("ordersn", mOrderSn)
//                    intent.setClass(this@UpOrderActivityKt, OrderDfkActivity::class.java)
//                    startActivity(intent)
//                    this@UpOrderActivity.finish()
                    toast("付款失败")

                }
                true
            }
            else -> {
                false
            }
        }
    }
    )

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


    private val addressObserver: Observer<Intent> = Observer {
        it?.run {
            getAddress(this)
        }
    }

    private var noteDialog=BasePopWindow(this)
    private lateinit var noteBinding: LayoutNoteBinding

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
            if (intent.iprovince.isNullOrBlank()){
                toast("请先完善地址信息")
            }else{
                body?.run {
                    note=mBinding.tvOrdernote.text.toString()
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
                    mBinding.layoutAddress02.visibility=View.VISIBLE
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
                    mBinding.giftList.linear().setup {
                        addType<Product01>(R.layout.item_uporder)
                        addType<Gift>(R.layout.item_gift)
                        val list= mutableListOf<Any>()
                        list.add(product)
                        gift?.forEach {_gift->
                            list.add(_gift)
                        }
                        models=list
                        mTitle=name?:""
                    }
//                    mBinding.tvOrdertitle.text=name?:""
//                    mBinding.ivOrderpic.load(imgShow?:"")
////                    mBinding.ivOrderpic.load(imgShow?:"")
//                    mBinding.tvPrice.text="￥".plus(priceActual?:"")
//                    mTitle=name?:""
//
//                    //赠品
//                    gift?.run {
//                        if (isNotEmpty()){
//
//                        }
//                    }
                }
//                mBinding.tvTotalPrice.text=(priceActual?:0.00).toString()

                val num=intent.inum
                mBinding.tvTotalPrice.text=(priceActual?:0.00).toString()

                val detailRequestBody=UpOrderDetailRequestBody(product_id=intent.iproductId.toString(), sku_id = intent.isku?:"",product_quantity=num.toString())
                val payAmount=mBinding.tvTotalPrice.text.toString()//实付金额
                val totalAmount=(priceShow?:0.00).times(num).toString()//总金额
                body= UpOrderRequestBody(order_type = "0",total_amount=totalAmount,
                    pay_amount=payAmount,freight_amount=freight.toString(), note = "",
                    coupon_code = intent.icoupon?:"", coupon_amount = "0", detail = listOf(detailRequestBody), address_id = intent.iid.toString())//下单时携带的body

            }

        })
        //返回上一页
        mBinding.titleOrder.leftView.fastClick {
            finish()
        }
        //activity启动器

        LiveEventBus.get<Intent>("refresh").observe(this, addressObserver)

        //去修改地址
        mBinding.layoutAddress02.fastClick {
            intent.iflag="change"
//            intent.setClass(this@UpOrderActivity,AddressActivity::class.java)

            start(this@UpOrderActivity,AddressActivity().javaClass,intent)
        }
        //规格
        intent.iskuName?.run {
            mBinding.tvSpecifications.visibility=View.VISIBLE
            mBinding.tvSpecifications.text="商品规格:".plus(this)//规格
        }
//        备注
        mBinding.tvOrdernote.fastClick {
            showNote()
        }
    }

    /***
     * 支付弹框
     */
    private fun showPay(){
        payDialog.contentView=payBinding.root
        payBinding.orderPopnumber.text=mBinding.tvTotalPrice.text
        val amount=payBinding.orderPopnumber.text//付款价格

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
        //立即支付
        payBinding.btnZhifu.fastClick {
            when(mPayWay){
                1->{
                    weiChatPay(amount.toString(),mTitle,mOrderSn)
                }
                2->{
                    aliPay(amount.toString(),mTitle,mOrderSn)
                }
            }
        }

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
            intent.inum.toString(),intent.icoupon?:""
        ).toMap()
    }

    /***
     * 微信支付
     */
    private fun weiChatPay(amount:String,subject:String,order:String){
        viewModel.weiChatPay(amount, subject, order).observe(this, Observer {
            it?.run {
               when(code){
                   1000->{
                       val weChatAppBean=data
                       weChatAppBean?.run {
                           val mWxApi = WXAPIFactory.createWXAPI(this@UpOrderActivity, appid)
                           mWxApi.registerApp(appid)

                           Handler(mainLooper).post {
                               //实体类
                               val req = PayReq()
                               //微信的id
                               req.appId = appid
                               //商户号
                               req.partnerId = partnerid
                               //        //预支付订单
                               req.prepayId = prepayid
                               //随机的字符串
                               req.nonceStr = noncestr
                               //时间戳
                               req.timeStamp = timestamp + ""
                               //扩展字段
                               req.packageValue = packageX
                               //支付签名
                               req.sign = sign
                               // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
//        //3.调用微信支付sdk支付方法
                               mWxApi.sendReq(req)
                           }
                       }

                   }
                   401->{
                       toast("登录状态失效,请重新登录")
                   }else->{
                       toast(message.toString())
                   }
               }
            }
        })
    }

    /****
     * 支付宝支付
     */
    private fun aliPay(amount:String,subject:String,order:String){
        viewModel.aliPay(amount, subject, order).observe(this, Observer {
            it?.run {
                when(code){
                    1000->{
                        Thread {
                            val alipay = PayTask(this@UpOrderActivity)
                            val result = alipay.payV2(data, true)
                            val message = Message()
                            message.what = SDK_PAY_FLAG
                            message.obj = result
                            mHandler.sendMessage(message)
                        }.start()
                    }
                }
            }
        })
    }

    private fun getAddress(i: Intent){
        val name=i.getStringExtra("name")
        val phone=i.getStringExtra("phone")
        val province = i.getStringExtra("province")
        val city = i.getStringExtra("city")
        val area = i.getStringExtra("area")
        val detail = i.getStringExtra("detail")
        mBinding.tvAddressDetail.text = province.plus(city).plus(area).plus(detail)
        mBinding.addressName.text = name
        mBinding.addressPhone.text = phone
        mBinding.layoutAddress02.visibility = View.VISIBLE

        intent.iprovince=province
        intent.icity=city
        intent.iarea=area
        intent.iaddress=detail
        intent.iname=name
        intent.iphone=phone
    }

    /***
     * 备注
     */
    private fun showNote(){
        val view=layoutInflater.inflate(R.layout.layout_note,null)
        noteBinding= LayoutNoteBinding.bind(view)
        noteBinding.btnNote.fastClick {
            mBinding.tvOrdernote.text=noteBinding.edtNote.text.toString()
            noteDialog.dismiss()
        }
        noteBinding.noteClose.fastClick {
            noteDialog.dismiss()
        }
        noteDialog.contentView=noteBinding.root
        noteDialog.setOutSideDismiss(true).setOutSideTouchable(true)
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

    override fun onDestroy() {
        super.onDestroy()
        LiveEventBus.get<Intent>("refresh").removeObserver(addressObserver)
    }
}