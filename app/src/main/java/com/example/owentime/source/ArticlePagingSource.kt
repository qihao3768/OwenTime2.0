package com.example.owentime.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.owentime.bean.ArticleData
import com.example.owentime.bean.GoodsBean
import com.example.owentime.net.ApiService

class ArticlePagingSource(private val apiService: ApiService):PagingSource<Int, GoodsBean>() {

    override fun getRefreshKey(state: PagingState<Int, GoodsBean>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GoodsBean> {

        val bean=GoodsBean()
        val list= mutableListOf<GoodsBean>(bean,bean,bean,bean,bean,bean,bean,bean,bean,bean)
        return try {
            val page = params.key ?: 0
            val pageSize = params.loadSize
            val repoResponse = list
//            val repoResponse = apiService.article(page)
//            val repoItems = repoResponse.data.datas
            val repoItems = list
            val prevKey = if (page > 1) page - 1 else null
            val nextKey = if (repoItems.isNotEmpty()) page + 1 else null
            LoadResult.Page(repoItems, prevKey, nextKey)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}