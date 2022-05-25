package com.example.owentime.ui

import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.owentime.R
import com.example.owentime.base.BaseActivity
import com.example.owentime.bean.Register
import com.example.owentime.databinding.ActivityLoginBinding
import com.example.owentime.toast
import com.example.owentime.vm.LoginViewModel

import com.jeremyliao.liveeventbus.LiveEventBus
import com.tencent.mmkv.MMKV

class LoginActivity : BaseActivity(R.layout.activity_login) {
    private val mBinding by viewBinding(ActivityLoginBinding::bind)
    private val mViewModel by viewModels<LoginViewModel>()
    private val mmkv= MMKV.defaultMMKV()
    private var mLoginOregis=0

    override fun initData() {
      mBinding.loginRegis.setOnClickListener {
          when(mLoginOregis){
              0->{
                  mBinding.btnLogin.text="注册"
                  mBinding.loginRegis.text="返回登录"
                  mLoginOregis=1
              }
              1->{
                  mBinding.btnLogin.text="登录"
                  mBinding.loginRegis.text="注册账号"
                  mLoginOregis=0
              }
          }
      }
        mBinding.btnLogin.setOnClickListener {
            when(mLoginOregis){
                0->{
                    login(mBinding.loginAccount.text.toString(),mBinding.loginPass.text.toString())
                }
                1->{

                }
            }
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
}