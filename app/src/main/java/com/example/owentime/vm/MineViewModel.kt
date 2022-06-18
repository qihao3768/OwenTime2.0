package com.example.owentime.vm

import androidx.lifecycle.MutableLiveData
import com.example.owentime.base.BaseViewModel
import com.example.owentime.bean.User
import com.example.owentime.resp.OwenRepo

class MineViewModel : BaseViewModel() {
    private val _userReps by lazy { OwenRepo() }
    private var _userData= MutableLiveData<User>()

//    val article get() =_articleData

    fun getUser(token:String): MutableLiveData<User> {
        launchUI {
            val result=_userReps.getUser(token).data
            _userData.value= result!!

        }
        return _userData
    }

}