package com.example.owentime.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.owentime.bean.Integral
import com.example.owentime.net.ApiService

class IntegralPagingSource(private val apiService: ApiService):PagingSource<Int,Integral>() {
    override fun getRefreshKey(state: PagingState<Int,Integral>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int,Integral> {
        return try {
            val page = params.key ?: 0
            val pageSize = params.loadSize
            val repoResponse = apiService.integralList(page)
            val repoItems = repoResponse.data.datas
            val prevKey = if (page > 1) page - 1 else null
            val nextKey = if (repoItems.isNotEmpty()) page + 1 else null
            LoadResult.Page(repoItems, prevKey, nextKey)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}