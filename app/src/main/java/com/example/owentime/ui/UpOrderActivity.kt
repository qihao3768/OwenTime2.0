package com.example.owentime.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.owentime.R
import com.example.owentime.base.BaseActivity
import com.example.owentime.base.BasePopWindow
import com.example.owentime.databinding.ActivityProductDetailBinding
import com.example.owentime.databinding.ActivityUpOrderBinding
import com.example.owentime.databinding.LayoutSpecificationsBinding
import com.example.owentime.databinding.SelectbuywayLayout2Binding
import com.example.owentime.start
import com.gyf.immersionbar.ktx.immersionBar
import razerdp.util.animation.AnimationHelper
import razerdp.util.animation.TranslationConfig

class UpOrderActivity : BaseActivity(R.layout.activity_up_order) {
    private val mBinding by viewBinding (ActivityUpOrderBinding::bind)
    private var payDialog = BasePopWindow(this)
    private lateinit var payBinding: SelectbuywayLayout2Binding
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
    }
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
}