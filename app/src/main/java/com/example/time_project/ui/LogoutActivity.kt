package com.example.time_project.ui

import androidx.activity.viewModels
import androidx.lifecycle.Observer
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.time_project.ActivityManager
import com.example.time_project.R
import com.example.time_project.base.BaseActivity
import com.example.time_project.databinding.ActivityLogoutBinding
import com.example.time_project.fastClick
import com.example.time_project.toast
import com.example.time_project.vm.OwenViewModel
import com.gyf.immersionbar.ktx.immersionBar
import com.jeremyliao.liveeventbus.LiveEventBus
import com.tencent.mmkv.MMKV

class LogoutActivity : BaseActivity(R.layout.activity_logout) {
    private val mBinding by viewBinding(ActivityLogoutBinding::bind)
    private val mViewModel by viewModels<OwenViewModel>()
    override fun initData() {
        immersionBar {
            statusBarColor(R.color.white)
            keyboardEnable(true)
            statusBarDarkFont(true)
            fitsSystemWindows(true)
        }
        mBinding.btnLogout.setOnClickListener {
            // TODO: 调用注销接口
            mViewModel.logOff().observe(this, Observer {
                it?.run {
                    when(code){
                        1000->{
                            toast("注销成功")
                            MMKV.defaultMMKV().remove("token")
                            LiveEventBus.get<String>("logout").post("logout")
                            ActivityManager.instance.removeActivity(this@LogoutActivity)
                            ActivityManager.instance.removeActivity(SettingActivity())
                        }
                    }
                }

            })
        }

        mBinding.logoutTitle.leftView.fastClick {
            finish()
        }
    }
    
    
}