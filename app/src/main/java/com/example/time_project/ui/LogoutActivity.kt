package com.example.time_project.ui

import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.time_project.R
import com.example.time_project.base.BaseActivity
import com.example.time_project.databinding.ActivityLogoutBinding
import com.gyf.immersionbar.ktx.immersionBar

class LogoutActivity : BaseActivity(R.layout.activity_logout) {
    private val mBinding by viewBinding(ActivityLogoutBinding::bind)
    override fun initData() {
        immersionBar {
            statusBarColor(R.color.white)
            keyboardEnable(true)
            statusBarDarkFont(true)
            fitsSystemWindows(true)
        }
        mBinding.btnLogout.setOnClickListener {
            // TODO: 调用注销接口 
        }
    }
    
    
}