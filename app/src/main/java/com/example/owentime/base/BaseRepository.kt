package com.example.owentime.base

import android.util.Log
import com.example.owentime.toast
import com.jeremyliao.liveeventbus.LiveEventBus
import com.jeremyliao.liveeventbus.core.LiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

open class BaseRepository {

    suspend fun <T : Any> request(call: suspend () -> BaseResponse<T?>): BaseResponse<T?> {
        val mContinue:Boolean=true
        return withContext(Dispatchers.IO) {
            call.invoke()

        }.apply {
            Log.d("RESP","接口返回数据---------->,${this}")
//            if (data==null){
//                when(code){
//                    1000->this
//                    1001->{
//                        "缺少参数"
//                    }
//                    else->{
//                        toast(message.toString())}
//                }
//            }else{
//                when (code) {
//                    1000 -> this
//                    401 -> "登录状态失效"
//                    else -> {}
//
//                }
//            }

        }
    }

}