package com.example.owentime.jsonadapter

import com.example.owentime.EmptyStringToOjb
import com.example.owentime.bean.BaseModel
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

class StringToObjAdapter {
    @ToJson
    fun toJson( text: String): String {
        // 什么都不做
        return text
    }

    @EmptyStringToOjb
    @FromJson
     fun fromJson(jsonStr: String):ArrayList<Any>{
        if (jsonStr.isEmpty()){
            return arrayListOf()
        }
        return arrayListOf(jsonStr)
    }


}