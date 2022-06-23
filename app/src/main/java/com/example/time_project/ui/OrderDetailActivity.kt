package com.example.time_project.ui

import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.time_project.R
import com.example.time_project.base.BaseActivity
import com.example.time_project.databinding.ActivityOrderDetailBinding
import com.example.time_project.fastClick
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

        mBinding.titleOrder.leftView.fastClick {
            finish()
        }
    }
}