package com.example.owentime.ui

import android.os.CountDownTimer
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doBeforeTextChanged
import androidx.lifecycle.Observer
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.owentime.*
import com.example.owentime.base.BaseActivity
import com.example.owentime.bean.Register
import com.example.owentime.databinding.ActivityLoginBinding
import com.example.owentime.vm.LoginViewModel
import com.example.owentime.web.WebActivity
import com.gyf.immersionbar.ktx.immersionBar
import com.hjq.shape.view.ShapeButton
import com.hjq.shape.view.ShapeCheckBox

import com.jeremyliao.liveeventbus.LiveEventBus
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
            val target=if (mmkv.decodeBool("islogin",false)){
                MainActivity()
            }else{
                PerfectActivity()
            }
            start(this@LoginActivity,target.javaClass,true)

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
            toast("短信已发送,请注意查收")
        }
    }

    private fun login(username:String,password:String){
        mViewModel.login(username, password).observe(this, Observer {
            if (it != null) {
                toast("登录成功")
                MMKV.defaultMMKV().encode("user",it)
                LiveEventBus.get<Register>("user").post(it)
                finish()
            }
        })
    }
    private fun register(username:String,password:String){
        mViewModel.login(username, password).observe(this, Observer {
            if (it != null) {
                Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show()
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