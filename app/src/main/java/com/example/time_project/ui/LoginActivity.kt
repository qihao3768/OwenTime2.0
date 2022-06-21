package com.example.time_project.ui

import android.content.Intent
import android.os.CountDownTimer
import androidx.activity.viewModels
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.time_project.*
import com.example.time_project.base.BaseActivity

import com.example.time_project.databinding.ActivityLoginBinding
import com.example.time_project.util.IntentExtraString
import com.example.time_project.vm.LoginViewModel
import com.example.time_project.web.WebActivity
import com.gyf.immersionbar.ktx.immersionBar
import com.hjq.shape.view.ShapeCheckBox

import com.tencent.mmkv.MMKV
import java.util.*

class LoginActivity : BaseActivity(R.layout.activity_login) {
    private val mBinding by viewBinding(ActivityLoginBinding::bind)
    private val mViewModel by viewModels<LoginViewModel>()
    //短信倒计时
    private lateinit var timer: CountDownTimer

    private val TIME=60000L
    private val STEP=1000L
    private lateinit var mmkv: MMKV

    private var mKeyCode:String=""

    private lateinit var mToken:String

    companion object IntentOptions{
        var Intent.token by IntentExtraString("token")
    }
    override fun initData() {
        mmkv = MMKV.defaultMMKV()

        immersionBar {
            statusBarColor(R.color.white)
            keyboardEnable(false)
            statusBarDarkFont(true)
            fitsSystemWindows(true)
        }
        mBinding.tvGohome.setOnClickListener {
            finish()
//            start(this@LoginActivity,MainActivity().javaClass,true)
        }
        mBinding.btnLogin.isEnabled=false
        var color:Int
        mBinding.edtPhone.doAfterTextChanged {
            mBinding.btnLogin.isEnabled=if (it.toString().isNotEmpty() && mBinding.edtSms.text.toString().isNotEmpty()){
                color=getColor(R.color.FE9520)
                true
            }else{
                color=getColor(R.color.FFC482)
                false
            }
            mBinding.btnLogin.shapeDrawableBuilder.setSolidColor(color).intoBackground()
        }
        mBinding.edtSms.doAfterTextChanged {
            mBinding.btnLogin.isEnabled= if (it.toString().isNotEmpty() && mBinding.edtPhone.text.toString().isNotEmpty()){
                color=getColor(R.color.FE9520)
                true
            }else{
                color=getColor(R.color.FFC482)

                false
            }
            mBinding.btnLogin.shapeDrawableBuilder.setSolidColor(color).intoBackground()
        }

        mBinding.btnLogin.setOnClickListener {
            mBinding.loginCheck.checked("请先查看并勾选相关协议")?:return@setOnClickListener
            if (mKeyCode.isNullOrBlank()){
                toast("请先获取短信验证码")
                return@setOnClickListener
            }
            val phone=mBinding.edtPhone.text.toString()
            val sms=mBinding.edtSms.text.toString()
            login(phone,sms)
        }
        mBinding.ivPhoneclear.setOnClickListener {
            mBinding.edtPhone.text.clear()
        }
        //协议
        mBinding.tvUserAgreement.setOnClickListener {
            start(this@LoginActivity,WebActivity().javaClass,"url",AppConfig.SERVICE_AGREEMENT_URL)
        }
        mBinding.tvPrivacyAgreement.setOnClickListener {
            start(this@LoginActivity,WebActivity().javaClass,"url",AppConfig.PRIVACY_AGREEMENT_URL)
        }
        mBinding.tvChildAgreement.setOnClickListener {
            start(this@LoginActivity,WebActivity().javaClass,"url",AppConfig.CHILDREN_AGREEMENT_URL)
        }

        mBinding.tvSms.setOnClickListener {
            val phone=mBinding.edtPhone.checked("请输入手机号")?:return@setOnClickListener
            mBinding.edtPhone.checkLength(11,"请输入11位手机号")?:return@setOnClickListener
            timer = object : CountDownTimer(TIME,STEP) {
                override fun onTick(p0: Long) {
                    mBinding.tvSms.text=(p0/1000).toString().plus("s")
                    mBinding.tvSms.isClickable=false

                }

                override fun onFinish() {
                    mBinding.tvSms.text="重新发送"
                    mBinding.tvSms.isClickable=true
                }

            }
            timer.start()
            getSms(mBinding.edtPhone.text.toString())
            toast("短信已发送,请注意查收")
        }
    }

    /***
     * 获取验证码
     */
    private fun getSms(phone:String){
        mViewModel.sendSms(phone).observe(this, Observer {
            // TODO:
            it?.run {
                mKeyCode= keyCode?:""
            }
        })
    }

    /***
     * 登录
     */
    private fun login(phone: String,sms:String){

        mViewModel.login(phone, sms, mKeyCode).observe(this, Observer {
            it?.run {
                intent.token=accessToken?:""
                mmkv.encode("token",intent.token)//每次登录更新一下token
                when(infoFlag){
                    0->{
                        start(this@LoginActivity,PerfectActivity().javaClass,intent)
                    }
                    1->{
                        start(this@LoginActivity,MainActivity().javaClass,intent)
                    }
                }

            }
        })
    }

    private fun ShapeCheckBox.checked(msg:String):String?{
        return if (this.isChecked){
            ""
        }else{
            toast(msg)
            null
        }
    }

}