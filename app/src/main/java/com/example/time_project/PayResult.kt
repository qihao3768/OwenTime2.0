package com.example.time_project

import android.text.TextUtils

class PayResult(rawResult: Map<String, String>) {
    /**
     * @return the resultStatus
     */
    var resultStatus: String? = null

    /**
     * @return the result
     */
    var result: String? = null

    /**
     * @return the memo
     */
    var memo: String? = null

    override fun toString(): String {
        return ("resultStatus={" + resultStatus + "};memo={" + memo
                + "};result={" + result + "}")
    }

    init {
        rawResult.run {
            for (key in keys) {
                when {
                    TextUtils.equals(key, "resultStatus") -> {
                        resultStatus = this[key]
                    }
                    TextUtils.equals(key, "result") -> {
                        result = this[key]
                    }
                    TextUtils.equals(key, "memo") -> {
                        memo = this[key]
                    }
                }
            }
        }


    }
}
