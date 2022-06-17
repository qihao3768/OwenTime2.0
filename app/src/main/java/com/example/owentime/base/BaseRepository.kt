package com.example.owentime.base

import android.util.Log
import com.jeremyliao.liveeventbus.LiveEventBus
import com.jeremyliao.liveeventbus.core.LiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

open class BaseRepository {

    suspend fun <T : Any> request(call: suspend () -> BaseResponse<T>): BaseResponse<T> {
        return withContext(Dispatchers.IO) {
            call.invoke()
        }.apply {
            Log.d("RESP","接口返回数据---------->,${this}")
            if (data==null){

            }
            when (code) {
                1000 -> this
                401 -> "登录状态失效"
//                403 -> ""
//                404 -> ""
//                500 -> message

//                LiveEventBus.get("message",String::class.java).post(message)
                else -> {}

            }
        }
    }

}