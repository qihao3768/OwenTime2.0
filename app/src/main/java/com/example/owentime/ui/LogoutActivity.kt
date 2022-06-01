package com.example.owentime.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.owentime.R
import com.example.owentime.base.BaseActivity
import com.example.owentime.databinding.ActivityLogoutBinding
import com.example.owentime.databinding.ActivitySettingBinding
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