package com.example.owentime.resp

import com.example.owentime.base.BaseRepository
import com.example.owentime.base.BaseResponse
import com.example.owentime.bean.User
import com.example.owentime.net.RetrofitClient

class UserInfoRepo :BaseRepository(){
    private val mService by lazy {
        RetrofitClient.service
    }
//    suspend fun user(): BaseResponse<User> = request {
//        mService.user()
//    }
}