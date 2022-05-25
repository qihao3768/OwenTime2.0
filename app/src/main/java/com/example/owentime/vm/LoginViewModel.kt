package com.example.owentime.vm

import androidx.lifecycle.MutableLiveData
import com.example.owentime.base.BaseViewModel
import com.example.owentime.bean.Register
import com.example.owentime.resp.LoginRepo

class LoginViewModel:BaseViewModel() {
    private val _loginReps by lazy { LoginRepo() }
    private var _loginData= MutableLiveData<Register>()

//    val article get() =_articleData

    fun login(username:String,password:String): MutableLiveData<Register> {
        launchUI {
            val result=_loginReps.login(username, password).data
            _loginData.value= result

        }
        return _loginData

    }
}