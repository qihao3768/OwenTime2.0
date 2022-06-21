package com.example.time_project.base

import com.google.gson.TypeAdapter
import okhttp3.ResponseBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Converter
import java.io.IOException

class BaseResponseBodyConverter<T>(private val adapter: TypeAdapter<T>) :
    Converter<ResponseBody, T> {
    @Throws(IOException::class)
    override fun convert(value: ResponseBody): T {
        val jsonString = value.string()
        return try {
            val `object` = JSONObject(jsonString)
            val code = `object`.getInt("status_code")
            if (200 != code) {
                val data: String
                //错误信息
                data = if (code == LOG_OUT_TIME) {
                    "登录失效，请重新登录"
                } else {
                    `object`.getString("message")
                }
                throw BaseException(code, data)
            }
            //正确返回整个json
            adapter.fromJson(jsonString)
        } catch (e: JSONException) {
            e.printStackTrace()
            throw BaseException(BaseException.PARSE_ERROR_MSG)
        } finally {
            value.close()
        }
    }

    companion object {
        /**
         * 登陆失效
         */
        private const val LOG_OUT_TIME = 401
    }
}