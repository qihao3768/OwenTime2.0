package com.example.time_project.vm

import androidx.lifecycle.MutableLiveData
import com.example.time_project.base.BaseResponse
import com.example.time_project.base.BaseViewModel
import com.example.time_project.bean.login.LoginModel
import com.example.time_project.bean.login.SmsModel

import com.example.time_project.resp.OwenRepo

class LoginViewModel:BaseViewModel() {
    private val _loginReps by lazy { OwenRepo() }

//发送短信
    private val _smsData=MutableLiveData<BaseResponse<SmsModel?>>()

    fun sendSms(phone:String):MutableLiveData<BaseResponse<SmsModel?>>{
        launchUI {
            val result=_loginReps.sms(phone)
            _smsData.value = result

        }
        return _smsData
    }

    private val _login=MutableLiveData<LoginModel?>()
    fun login(phone:String,sms:String,key:String):MutableLiveData<LoginModel?>{
        launchUI {
            val result=_loginReps.login(phone,sms,key)
            _login.value = result.data

        }
        return _login
    }
}