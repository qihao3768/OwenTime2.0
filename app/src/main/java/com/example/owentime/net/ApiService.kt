package com.example.owentime.net

import com.example.owentime.base.BaseResponse
import com.example.owentime.bean.*
import retrofit2.http.*

interface ApiService {
    @GET("banner/json")
    suspend fun banner():BaseResponse<List<Banner>>

    @GET("article/list/{page}/json")
    suspend fun article(@Path("page") page : Int):BaseResponse<Page<ArticleData>>

    @GET("project/tree/json")
    suspend fun project():BaseResponse<List<ProjectTree>>

    @GET("project/list/{page}/json")
    suspend fun subProject(@Path("page") page: Int,@Query("cid") cid:Int):BaseResponse<Page<ProjectDetail>>

    @GET("wxarticle/chapters/json")
    suspend fun wxarticle():BaseResponse<List<WxArticleTree>>

    @GET("wxarticle/list/{id}/{page}/json")
    suspend fun wxarticleList(@Path("id") cid:Int, @Path("page") page: Int):BaseResponse<Page<WxArticleDetail>>

@POST("user/register")
@FormUrlEncoded
    suspend fun register(@Field("username") username:String,@Field("password") password:String,@Field("repassword") repassword:String):BaseResponse<Register>

@POST("user/login")
@FormUrlEncoded
    suspend fun login(@Field("username") username:String,@Field("password") password:String):BaseResponse<Register>

    @GET("user/lg/userinfo/json")
    suspend fun user():BaseResponse<User>

    @GET("lg/coin/list/{page}/json")
    suspend fun integralList(@Path("page") page: Int):BaseResponse<Page<Integral>>

    @GET("chapter/547/sublist/json")
    suspend fun tutorialList() :BaseResponse<List<Tutorial>>

    @GET("article/list/{page}/json")
    suspend fun directory(@Path("page") page : Int,@Query("cid") cid:Int,@Query("order_type") orderType:Int):BaseResponse<Page<ArticleData>>


    companion object {
        const val BASE_URL = "https://wanandroid.com/"
    }
}