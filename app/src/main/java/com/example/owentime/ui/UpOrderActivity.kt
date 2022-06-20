package com.example.owentime.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import com.example.owentime.R
import com.example.owentime.base.BaseActivity
import com.example.owentime.base.BasePopWindow
import com.example.owentime.bean.ConfirmOrderRequestBody
import com.example.owentime.databinding.ActivityProductDetailBinding
import com.example.owentime.databinding.ActivityUpOrderBinding
import com.example.owentime.databinding.LayoutSpecificationsBinding
import com.example.owentime.databinding.SelectbuywayLayout2Binding
import com.example.owentime.fastClick
import com.example.owentime.load
import com.example.owentime.start
import com.example.owentime.ui.ProductDetailActivity.IntentOptions.icode
import com.example.owentime.ui.ProductDetailActivity.IntentOptions.icoupon
import com.example.owentime.ui.ProductDetailActivity.IntentOptions.inum
import com.example.owentime.ui.ProductDetailActivity.IntentOptions.isku
import com.example.owentime.util.IntentExtraString
import com.example.owentime.vm.OwenViewModel
import com.gyf.immersionbar.ktx.immersionBar
import okhttp3.internal.http.HTTP_GONE
import razerdp.util.animation.AnimationHelper
import razerdp.util.animation.TranslationConfig

class UpOrderActivity : BaseActivity(R.layout.activity_up_order) {
    private val mBinding by viewBinding (ActivityUpOrderBinding::bind)
    private var payDialog = BasePopWindow(this)
    private lateinit var payBinding: SelectbuywayLayout2Binding

    private val viewModel by viewModels<OwenViewModel>()

    companion object IntentOptions{
        var Intent.iprovince by IntentExtraString("province")//商品代码
        var Intent.icity by IntentExtraString("city")//skuid
        var Intent.iarea by IntentExtraString("area")//购买数量
        var Intent.iname by IntentExtraString("name")//优惠券id
        var Intent.iphone by IntentExtraString("phone")//优惠券id
        var Intent.iaddress by IntentExtraString("address")//优惠券id
        var Intent.iid by IntentExtraString("id")//优惠券id
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
            showPay()
        }
//订单确认
        viewModel.confirmPage(initParams()).observe(this, Observer {
            it?.run {

                mBinding.orderPrice.text="￥".plus(priceShow)
                mBinding.tvOrdernum.text="x".plus(intent.inum)
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
                }
                product?.run {
                    mBinding.tvOrdertitle.text=name?:""
                    mBinding.ivOrderpic.load(imgShow?:"")
                    mBinding.ivOrderpic.load(imgShow?:"")
                    mBinding.tvPrice.text="￥".plus(priceActual?:"")
                }
                mBinding.tvTotalPrice.text=(priceActual?:0.00).toString()

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