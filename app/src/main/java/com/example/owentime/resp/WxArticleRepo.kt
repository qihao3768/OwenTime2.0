package com.example.owentime.resp

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.owentime.base.BaseRepository
import com.example.owentime.base.BaseResponse
import com.example.owentime.bean.*
import com.example.owentime.net.RetrofitClient
import com.example.owentime.source.WxArticlePagingSource
import kotlinx.coroutines.flow.Flow

class WxArticleRepo:BaseRepository(){
    private val mService by lazy {
        RetrofitClient.service
    }
    suspend fun wxarticle(): BaseResponse<List<WxArticleTree>> = request {
        mService.wxarticle()
    }
}

class WxArticleListRepo():BaseRepository(){
    private val PAGE_SIZE=20
    private val mService by lazy {
        RetrofitClient.service
    }
    fun wxArticleList(cid:Int): Flow<PagingData<WxArticleDetail>> {

        return Pager(config = PagingConfig(PAGE_SIZE), pagingSourceFactory = {
            WxArticlePagingSource(mService,cid)
        }).flow

    }
}