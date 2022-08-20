package com.example.time_project.ui

import android.content.Intent
import android.provider.Settings
import android.provider.Settings.EXTRA_APP_PACKAGE
import android.provider.Settings.EXTRA_CHANNEL_ID
import android.view.Gravity
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.time_project.*
import com.example.time_project.base.BaseActivity
import com.example.time_project.base.BasePopWindow
import com.example.time_project.databinding.ActivitySettingBinding
import com.example.time_project.databinding.LayoutExitBinding
import com.example.time_project.util.DataCleanManager
import com.example.time_project.util.IntentExtra.Companion.iSkip
import com.example.time_project.vm.OwenViewModel
import com.example.time_project.vm.UserViewModel
import com.example.time_project.web.WebActivity
import com.gyf.immersionbar.ktx.immersionBar
import com.jeremyliao.liveeventbus.LiveEventBus
import com.tencent.mmkv.MMKV
import razerdp.util.animation.AnimationHelper
import razerdp.util.animation.TranslationConfig

class SettingActivity : BaseActivity(R.layout.activity_setting) {
    private val mBinding by viewBinding(ActivitySettingBinding::bind)

    private lateinit var exitBinding:LayoutExitBinding
    private lateinit var exitDialog:BasePopWindow

    private val mViewModel by viewModels<OwenViewModel>()

    override fun initData() {
        immersionBar {
            statusBarColor(R.color.white)
            keyboardEnable(true)
            statusBarDarkFont(true)
            fitsSystemWindows(true)
        }
        val view=layoutInflater.inflate(R.layout.layout_exit,null)
        exitBinding = LayoutExitBinding.bind(view)

        mBinding.btnExit.setOnClickListener {
            logout("确认退出登录?","退出登录")
        }
        mBinding.layoutSetting04.setOnClickListener {
//            logout("确认注销账户?","注销账户",1)
            start(this@SettingActivity,LogoutActivity().javaClass,false)
        }
        mBinding.layoutSettingAccount.checkLogin(this, object : TodoListener {
            override fun todo() {
                intent.iSkip=true
                start(this@SettingActivity,PerfectActivity().javaClass, intent)
            }
        })
        mBinding.layoutSetting01.setOnClickListener {
            val intent =Intent(this@SettingActivity,NoticeActivity().javaClass)
            startActivity(intent)
        }
        mBinding.cacheMb.text=DataCleanManager.getTotalCacheSize(this)
        mBinding.layoutSetting02.setOnClickListener {
            DataCleanManager.clearAllCache(this)
            toast("清除成功")
            mBinding.cacheMb.text="0.0MB"
        }
        mBinding.layoutSetting05.setOnClickListener {
            start(this@SettingActivity,WebActivity().javaClass,"url",AppConfig.PRIVACY_AGREEMENT_URL)
        }
        mBinding.layoutSetting06.setOnClickListener {
            start(this@SettingActivity,WebActivity().javaClass,"url",AppConfig.SERVICE_AGREEMENT_URL)
        }
        mBinding.layoutSetting07.setOnClickListener {
            start(this@SettingActivity,WebActivity().javaClass,"url",AppConfig.CHILDREN_AGREEMENT_URL)
        }

        mBinding.layoutSetting08.setOnClickListener {
            start(this@SettingActivity,FeedBackActivity().javaClass,false)
        }

        mBinding.settingTitle.leftView.fastClick {
            finish()
        }



    }

    /***
     * 退出登录
     * 0 退出 1 注销
     */
    private fun logout(title:String,ok:String){
        exitDialog = BasePopWindow(this)
        exitBinding.tvExitTitle.text=title
        exitBinding.btnExitOk.text=ok
        exitBinding.btnExitOk.setOnClickListener {
            // TODO: 调用退出登录接口
            mViewModel.logOut().observe(this, Observer {
                toast("退出成功")
                MMKV.defaultMMKV().clearAll()
                LiveEventBus.get<String>("refresh").post("logout")
                exitDialog.dismiss()
                ActivityManager.instance.removeActivity(this)
            })
        }
        exitBinding.btnExitCancel.setOnClickListener {
            exitDialog.dismiss()
        }
        exitDialog.contentView = exitBinding.root

        exitDialog.setOutSideDismiss(true).setOutSideTouchable(true)
            .setPopupGravity(Gravity.CENTER)
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