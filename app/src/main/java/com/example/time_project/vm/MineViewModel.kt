package com.example.time_project.vm

import androidx.lifecycle.MutableLiveData
import com.example.time_project.base.BaseViewModel
import com.example.time_project.bean.User
import com.example.time_project.resp.OwenRepo

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