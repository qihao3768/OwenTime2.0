package com.example.owentime.base;

import java.io.IOException;

class BaseException : IOException {
    var errorMsg: String
        private set
    var errorCode = 0
        private set

    constructor(message: String) {
        errorMsg = message
    }

    constructor(errorMsg: String, cause: Throwable?) : super(errorMsg, cause) {
        this.errorMsg = errorMsg
    }

    constructor(errorCode: Int, message: String) {
        errorMsg = message
        this.errorCode = errorCode
    }

    companion object {
        /**
         * 解析数据失败
         */
        const val PARSE_ERROR_MSG = "解析数据失败"

        /**
         * 网络问题
         */
        const val BAD_NETWORK_MSG = "网络问题"

        /**
         * 连接错误
         */
        const val CONNECT_ERROR_MSG = "连接错误"

        /**
         * 连接超时
         */
        const val CONNECT_TIMEOUT_MSG = "连接超时"

        /**
         * 未知错误
         */
        const val OTHER_MSG = "未知错误"
    }
}