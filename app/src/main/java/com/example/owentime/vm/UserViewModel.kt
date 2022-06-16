package com.example.owentime.vm

import androidx.lifecycle.MutableLiveData
import com.example.owentime.base.BaseViewModel
import com.example.owentime.bean.LoginModel
import com.example.owentime.bean.PhotoModel

import com.example.owentime.resp.UserInfoRepo

class UserViewModel:BaseViewModel() {
    private val _userRepo by lazy { UserInfoRepo() }
    private val _user= MutableLiveData<PhotoModel>()

    fun upload(token:String,path:String): MutableLiveData<PhotoModel> {
        launchUI {
            val result=_userRepo.upload(token, path)
            _user.value = result.data

        }
        return _user
    }

    fun uploadInfo(token:String,name:String,sex:Int,birthday:String): MutableLiveData<PhotoModel> {
        launchUI {
            val result=_userRepo.uploadInfo(token,name,sex,birthday)
            _user.value = result.data

        }
        return _user
    }
}