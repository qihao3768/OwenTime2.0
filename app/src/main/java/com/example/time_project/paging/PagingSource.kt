//package com.example.time_project.paging
//
//import androidx.paging.PagingSource
//import androidx.paging.PagingState
//import com.example.time_project.bean.order.OrderListData
//import com.example.time_project.bean.yigou.Recommend
//import com.example.time_project.net.ApiService
//
//class OrderListPagingSource(private val apiService: ApiService): PagingSource<Int, OrderListData>() {
//    override fun getRefreshKey(state: PagingState<Int, OrderListData>): Int? {
//        return null
//    }
//
//    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, OrderListData> {
//        return try {
//            val page = params.key ?: 1
//            val pageSize = params.loadSize
//            val repoResponse = apiService.orderList("1",page.toString())
//            val repoItems = repoResponse.data?.orderlist
//            val prevKey = if (page > 1) page - 1 else null
//            val nextKey = if (repoItems?.isNotEmpty()==true) page + 1 else null
//            LoadResult.Page(repoItems!!, prevKey, nextKey)
//        } catch (e: Exception) {
//            LoadResult.Error(e)
//        }
//    }
//}
//
//class ProductPagingSource(private val apiService: ApiService): PagingSource<Int, Recommend>() {
//    override fun getRefreshKey(state: PagingState<Int, Recommend>): Int? {
//        return null
//    }
//
//    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Recommend> {
//        return try {
//            val page = params.key ?: 1
//            val pageSize = params.loadSize
//            val repoResponse = apiService.alreadyBuy(page.toString())
//            val repoItems = repoResponse.data?.recommend?.data
//            val prevKey = if (page > 1) page - 1 else null
//            val nextKey = if (repoItems?.isNotEmpty()==true) page + 1 else null
//            LoadResult.Page(repoItems!!, prevKey, nextKey)
//        } catch (e: Exception) {
//            LoadResult.Error(e)
//        }
//    }
//}