package com.example.owentime.resp

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.owentime.base.BaseRepository
import com.example.owentime.bean.ArticleData
import com.example.owentime.net.RetrofitClient
import com.example.owentime.source.DirectoryPagingSource
import kotlinx.coroutines.flow.Flow

class DirectoryRepo:BaseRepository() {
    private val PAGE_SIZE=20
    private val mService by lazy {
        RetrofitClient.service
    }
    fun directory(cid:Int,orderType:Int): Flow<PagingData<ArticleData>> {

        return Pager(config = PagingConfig(PAGE_SIZE), pagingSourceFactory = {
            DirectoryPagingSource(mService,cid, orderType)
        }).flow

    }
}