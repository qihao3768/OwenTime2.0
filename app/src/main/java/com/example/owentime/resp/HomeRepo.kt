package com.example.owentime.resp

import com.example.owentime.base.BaseRepository
import com.example.owentime.base.BaseResponse
import com.example.owentime.bean.HomeModel
import com.example.owentime.net.RetrofitClient


class BannerRepo():BaseRepository(){

    private val mService by lazy {
        RetrofitClient.service
    }
    suspend fun banner():BaseResponse<HomeModel?> = request {
        mService.banner()
    }
}
