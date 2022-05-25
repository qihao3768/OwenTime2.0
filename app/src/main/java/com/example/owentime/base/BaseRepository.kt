package com.example.owentime.base

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

open class BaseRepository {

    suspend fun <T : Any> request(call: suspend () -> BaseResponse<T>): BaseResponse<T> {
        return withContext(Dispatchers.IO) {
            call.invoke()
        }.apply {
            Log.d("RESP","接口返回数据---------->,${this}")
            when (errorCode) {
                0, 200 -> this
                -1001, 401 -> "请先登录"
                403 -> ""
                404 -> ""
                500 -> ""
                else -> ""

            }
        }
    }

}