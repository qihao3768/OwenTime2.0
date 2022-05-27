package com.example.owentime.ui

import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doBeforeTextChanged
import androidx.lifecycle.Observer
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.owentime.MainActivity
import com.example.owentime.R
import com.example.owentime.base.BaseActivity
import com.example.owentime.bean.Register
import com.example.owentime.databinding.ActivityLoginBinding
import com.example.owentime.start
import com.example.owentime.toast
import com.example.owentime.vm.LoginViewModel
import com.gyf.immersionbar.ktx.immersionBar
import com.hjq.shape.view.ShapeButton
import com.hjq.shape.view.ShapeCheckBox

import com.jeremyliao.liveeventbus.LiveEventBus
import com.tencent.mmkv.MMKV

class LoginActivity : BaseActivity(R.layout.activity_login) {
    private val mBinding by viewBinding(ActivityLoginBinding::bind)
    private val mViewModel by viewModels<LoginViewModel>()
    private val mmkv= MMKV.defaultMMKV()
    private var mLoginOregis=0

    override fun initData() {
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
            // TODO: login
            start(this@LoginActivity,PerfectActivity().javaClass,true)
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