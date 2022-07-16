package com.example.time_project.base

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.time_project.toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import kotlinx.coroutines.withTimeoutOrNull
import retrofit2.HttpException
import java.net.SocketTimeoutException

open class BaseViewModel : ViewModel(), LifecycleObserver {


    //运行在UI线程的协程
    fun launchUI(block: suspend CoroutineScope.() -> Unit) = viewModelScope.launch {
        try {
//            withTimeout(15 * 1000) {
//                block()
//            }
            withTimeoutOrNull(15 * 1000) {
                block()
            }
        } catch (e: Exception) {
            //此处接收到BaseRepository里的request抛出的异常
            //根据业务逻辑自行处理代码...

            when(e){
                is SocketTimeoutException ->{
                    toast("连接超时")
                }
                is HttpException ->{
                    toast("服务器错误")
                }else->{
                toast("出错了")
                }
            }
            println(e.printStackTrace())

        }
    }

    override fun onCleared() {
        super.onCleared()
    }
}