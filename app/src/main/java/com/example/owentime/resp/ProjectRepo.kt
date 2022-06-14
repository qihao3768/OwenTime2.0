package com.example.owentime.resp

import com.example.owentime.base.BaseRepository
import com.example.owentime.net.RetrofitClient

class ProjectRepo:BaseRepository(){
    private val mService by lazy {
        RetrofitClient.service
    }
//    suspend fun project(): BaseResponse<List<ProjectTree>> = request {
//        mService.project()
//    }
}
