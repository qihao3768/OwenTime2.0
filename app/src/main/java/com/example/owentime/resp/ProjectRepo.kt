package com.example.owentime.resp

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.owentime.base.BaseRepository
import com.example.owentime.base.BaseResponse
import com.example.owentime.bean.*
import com.example.owentime.net.RetrofitClient
import com.example.owentime.source.ProjectPagingSource
import kotlinx.coroutines.flow.Flow

class ProjectRepo:BaseRepository(){
    private val mService by lazy {
        RetrofitClient.service
    }
    suspend fun project(): BaseResponse<List<ProjectTree>> = request {
        mService.project()
    }
}

class SupProjectRepo():BaseRepository(){
    private val PAGE_SIZE=20
    private val mService by lazy {
        RetrofitClient.service
    }
    fun subProject(cid:Int): Flow<PagingData<GoodsBean>> {

        return Pager(config = PagingConfig(PAGE_SIZE), pagingSourceFactory = {
            ProjectPagingSource(mService,cid)
        }).flow

    }
}