package com.example.owentime.resp

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.owentime.base.BaseRepository
import com.example.owentime.bean.Integral
import com.example.owentime.net.RetrofitClient
import com.example.owentime.source.IntegralPagingSource
import kotlinx.coroutines.flow.Flow

class IntegralResp: BaseRepository(){
    private val mService by lazy {
        RetrofitClient.service
    }


    private val PAGE_SIZE=10

    fun integer(): Flow<PagingData<Integral>> {

        return Pager(config = PagingConfig(PAGE_SIZE), pagingSourceFactory = {
            IntegralPagingSource(mService)
        }).flow

    }
}