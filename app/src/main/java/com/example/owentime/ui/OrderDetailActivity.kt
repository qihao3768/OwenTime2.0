package com.example.owentime.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.owentime.R
import com.example.owentime.base.BaseActivity
import com.example.owentime.databinding.ActivityOrderDetailBinding
import com.example.owentime.databinding.ActivityOrderListBinding
import com.gyf.immersionbar.ktx.immersionBar

class OrderDetailActivity : BaseActivity(R.layout.activity_order_detail) {
    private val mBinding by viewBinding(ActivityOrderDetailBinding::bind)

    override fun initData() {
        immersionBar {
            titleBar(mBinding.titleOrder)
            keyboardEnable(true)
            statusBarDarkFont(true)
            fitsSystemWindows(true)
        }
    }
}