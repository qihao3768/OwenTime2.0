package com.example.owentime.resp

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.owentime.base.BaseRepository
import com.example.owentime.base.BaseResponse
import com.example.owentime.bean.ArticleData
import com.example.owentime.bean.Banner
import com.example.owentime.net.RetrofitClient
import com.example.owentime.source.ArticlePagingSource
import kotlinx.coroutines.flow.Flow


class BannerRepo():BaseRepository(){

    private val mService by lazy {
        RetrofitClient.service
    }
    suspend fun banner():BaseResponse<List<Banner>> = request {
        mService.banner()
    }
}

class ArticleRepo():BaseRepository(){
    private val PAGE_SIZE=20
    private val mService by lazy {
        RetrofitClient.service
    }
     fun article(): Flow<PagingData<ArticleData>> {

        return Pager(config = PagingConfig(PAGE_SIZE), pagingSourceFactory = {
            ArticlePagingSource(mService)
        }).flow

    }
}