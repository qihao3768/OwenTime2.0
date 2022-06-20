package com.example.owentime.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.owentime.R
import com.example.owentime.base.BaseActivity
import com.example.owentime.base.BasePopWindow
import com.example.owentime.bean.ConfirmOrderRequestBody
import com.example.owentime.databinding.ActivityProductDetailBinding
import com.example.owentime.databinding.ActivityUpOrderBinding
import com.example.owentime.databinding.LayoutSpecificationsBinding
import com.example.owentime.databinding.SelectbuywayLayout2Binding
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
                mBinding.tvOrdertitle.text=product?.name?:""
                mBinding.orderPrice.text="￥".plus(priceShow)
                mBinding.tvPrice.text="￥".plus(priceShow)
                mBinding.tvPrice.text="￥".plus(priceShow)
                mBinding.tvOrdernum.text="x".plus(intent.inum)
                address?.run {
                    mBinding.layoutAddress01.visibility= View.GONE
                    mBinding.groupAddress.visibility=View.VISIBLE
                    mBinding.addressName.text=name?:""
                    mBinding.addressPhone.text=phone?:""
                    val maddress=(province?:"").plus(city?:"").plus(area?:"").plus(address?:"")
                    mBinding.tvAddressDetail.text=maddress
                }
                mBinding.tvTotalPrice.text=(priceActual?:0.00).toString()
            }

        })
        //返回上一页
        mBinding.titleOrder.leftView.setOnClickListener {
            finish()
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