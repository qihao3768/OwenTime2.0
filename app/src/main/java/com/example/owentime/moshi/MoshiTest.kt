package com.example.owentime.moshi
import com.squareup.moshi.JsonClass

import com.squareup.moshi.Json
import com.squareup.moshi.Moshi


fun main(){
    val json="""{
  "code": 1000,
  "message": "success",
  "data": {
    "photo": "https"
  },
  "status_code": 200
}"""
    val moShi=Moshi.Builder().build()
    val m=moShi.adapter(MoShi::class.java).fromJson(json)
    println(m)
}

@JsonClass(generateAdapter = true)
data class MoShi(
    @Json(name = "code")
    var code: Int? = 0,
    @Json(name = "data")
    var data: Data? = Data(),
    @Json(name = "message")
    var message: String? = "",
    @Json(name = "status_code")
    var statusCode: Int? = 0
)

@JsonClass(generateAdapter = true)
data class Data(
    @Json(name = "photo")
    var photo: String? = ""
)
