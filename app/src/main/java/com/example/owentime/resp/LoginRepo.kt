package com.example.owentime.resp

import com.example.owentime.base.BaseRepository
import com.example.owentime.base.BaseResponse
import com.example.owentime.bean.Register
import com.example.owentime.net.RetrofitClient

class LoginRepo:BaseRepository(){
    private val mService by lazy {
        RetrofitClient.service
    }
    suspend fun login(username:String,password:String): BaseResponse<Register> = request {
        mService.login(username,password)
    }
}

class RegisRepo:BaseRepository(){
    private val mService by lazy {
        RetrofitClient.service
    }
    suspend fun register(username:String,password:String,repassword:String): BaseResponse<Register> = request {
        mService.register(username,password,repassword)
    }
}