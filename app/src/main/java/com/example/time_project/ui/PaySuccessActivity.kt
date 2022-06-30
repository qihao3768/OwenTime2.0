package com.example.time_project.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.time_project.*
import com.example.time_project.base.BaseActivity
import com.example.time_project.databinding.ActivityPaySuccessBinding
import com.example.time_project.databinding.ActivityPerfectBinding

class PaySuccessActivity : BaseActivity(R.layout.activity_pay_success) {
    private val mBinding by viewBinding(ActivityPaySuccessBinding::bind)
    override fun initData() {
        mBinding.btnGohome.fastClick {
            start(this@PaySuccessActivity,MainActivity().javaClass,true)
        }

        mBinding.btnGoorder.fastClick {
            toast("订单详情")
        }
    }
}