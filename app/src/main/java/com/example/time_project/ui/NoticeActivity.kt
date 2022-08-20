package com.example.time_project.ui

import android.content.Intent
import android.provider.Settings
import androidx.core.app.NotificationManagerCompat
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.time_project.App
import com.example.time_project.R
import com.example.time_project.base.BaseActivity
import com.example.time_project.databinding.ActivityNoticeBinding
import com.example.time_project.fastClick


class NoticeActivity : BaseActivity(R.layout.activity_notice) {
    private val mBinding by viewBinding(ActivityNoticeBinding::bind)
    override fun initData() {
        mBinding.noticeLeft.fastClick {
            finish()
        }
        mBinding.tvGoSetting.fastClick {
            val intent:Intent= Intent()
            intent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
            intent.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName())
            intent.putExtra(Settings.EXTRA_CHANNEL_ID, getApplicationInfo().uid)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        val manager = NotificationManagerCompat.from(applicationContext)

        val isOpened = manager.areNotificationsEnabled()
        if (isOpened){
            mBinding.tvGoSetting.text="已开启"
        }else{
            mBinding.tvGoSetting.text="已关闭"
        }

    }

}