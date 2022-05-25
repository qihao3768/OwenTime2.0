package com.example.owentime.vm

import androidx.lifecycle.MutableLiveData
import com.example.owentime.base.BaseViewModel
import com.example.owentime.bean.User
import com.example.owentime.resp.UserInfoRepo

class MineViewModel : BaseViewModel() {
    private val _loginReps by lazy { UserInfoRepo() }
    private var _loginData= MutableLiveData<User>()

//    val article get() =_articleData

    fun user(): MutableLiveData<User> {
        launchUI {
            val result=_loginReps.user().data
            _loginData.value= result!!

        }
        return _loginData

    }
}