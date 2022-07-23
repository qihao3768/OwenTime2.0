package com.example.time_project.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer

import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.time_project.*
import com.example.time_project.base.BaseActivity
import com.example.time_project.databinding.ActivityPaySuccessBinding


class PaySuccessActivity : BaseActivity(R.layout.activity_pay_success) {
    private val mBinding by viewBinding(ActivityPaySuccessBinding::bind)
    private lateinit var mCountDownTimer:CountDownTimer

    override fun initData() {
//        mBinding.btnGohome.fastClick {
//            start(this@PaySuccessActivity,MainActivity().javaClass,true)
//        }
//
//        mBinding.btnGoorder.fastClick {
//            toast("订单详情")
//        }

        mBinding.titlePayresult.leftView.fastClick {
            start(this,OrderListActivity().javaClass,true)
        }

        mCountDownTimer= object : CountDownTimer(3000,1000) {
            override fun onTick(p0: Long) {

            }

            override fun onFinish() {
                start(this@PaySuccessActivity,MainActivity().javaClass,true)
            }

        }
        mCountDownTimer.start()

    }
}