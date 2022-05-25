package com.example.owentime.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.owentime.bean.ArticleData
import com.example.owentime.net.ApiService

class DirectoryPagingSource(private val apiService: ApiService,private val cid:Int,private val orderType:Int):PagingSource<Int, ArticleData>() {
    override fun getRefreshKey(state: PagingState<Int, ArticleData>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticleData> {
        return try {
            val page = params.key ?: 0
            val pageSize = params.loadSize
            val repoResponse = apiService.directory(page,cid, orderType)
            val repoItems = repoResponse.data.datas
            val prevKey = if (page > 1) page - 1 else null
            val nextKey = if (repoItems.isNotEmpty()) page + 1 else null
            LoadResult.Page(repoItems, prevKey, nextKey)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}