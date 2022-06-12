package com.example.owentime.ui

import android.view.Gravity
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.owentime.*
import com.example.owentime.base.BaseActivity
import com.example.owentime.base.BasePopWindow
import com.example.owentime.databinding.ActivitySettingBinding
import com.example.owentime.databinding.LayoutExitBinding
import com.example.owentime.web.WebActivity
import com.gyf.immersionbar.ktx.immersionBar
import com.tencent.mmkv.MMKV
import razerdp.util.animation.AnimationHelper
import razerdp.util.animation.TranslationConfig
import kotlin.system.exitProcess

class SettingActivity : BaseActivity(R.layout.activity_setting) {
    private val mBinding by viewBinding(ActivitySettingBinding::bind)

    private lateinit var exitBinding:LayoutExitBinding
    private lateinit var exitDialog:BasePopWindow

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
            logout("确认退出登录?","退出登录",0)
        }
        mBinding.layoutSetting04.setOnClickListener {
//            logout("确认注销账户?","注销账户",1)
            start(this@SettingActivity,LogoutActivity().javaClass,false)
        }
        mBinding.layoutSettingAccount.checkLogin(this, object : TodoListener {
            override fun todo() {
                start(this@SettingActivity,PerfectActivity().javaClass,false)
            }
        })
        mBinding.layoutSetting05.setOnClickListener {
            start(this@SettingActivity,WebActivity().javaClass,"url",AppConfig.PRIVACY_AGREEMENT_URL)
        }
        mBinding.layoutSetting06.setOnClickListener {
            start(this@SettingActivity,WebActivity().javaClass,"url",AppConfig.SERVICE_AGREEMENT_URL)
        }
        mBinding.layoutSetting07.setOnClickListener {
            start(this@SettingActivity,WebActivity().javaClass,"url",AppConfig.CHILDREN_AGREEMENT_URL)
        }

    }

    /***
     * 退出登录
     * 0 退出 1 注销
     */
    private fun logout(title:String,ok:String,flag:Int){
        exitDialog = BasePopWindow(this)
        exitBinding.tvExitTitle.text=title
        exitBinding.btnExitOk.text=ok
        exitBinding.btnExitOk.setOnClickListener {
            // TODO: 调用退出登录接口
            when(flag) {
                0->{
                    // TODO: 调用退出接口
                    MMKV.defaultMMKV().remove("islogin")
                    exitProcess(0)
                }
                1->{
                    // TODO: 调用注销接口
                }
            }
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

    /***
     * 注销
     */
    private fun cancellation(){

    }
}