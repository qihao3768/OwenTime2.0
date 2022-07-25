package com.example.time_project.base

import com.example.time_project.BuildConfig


import com.example.time_project.net.Interceptor
import com.hjq.gson.factory.GsonFactory

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

import java.util.concurrent.TimeUnit


abstract class BaseRetrofitClient {

    companion object CLIENT {
        private const val TIME_OUT = 10L
    }

    private val client: OkHttpClient by lazy {
//        val cookieJar: ClearableCookieJar =
//            PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(App.getContext()))
        val builder = OkHttpClient.Builder()
                .addNetworkInterceptor(getHttpLoggingInterceptor())
            .addInterceptor(Interceptor())
            .readTimeout(TIME_OUT,TimeUnit.SECONDS)
            .writeTimeout(TIME_OUT,TimeUnit.SECONDS)
            .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
        handleBuilder(builder)
        builder.build()
    }

    private fun getHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            logging.level = HttpLoggingInterceptor.Level.BODY
        } else {
            logging.level = HttpLoggingInterceptor.Level.BASIC
        }
        return logging
    }

    abstract fun handleBuilder(builder: OkHttpClient.Builder)

//    private val json = Json {
//        encodeDefaults = true
//        coerceInputValues = true
//        ignoreUnknownKeys = true
//    }

//    @OptIn(ExperimentalSerializationApi::class)
    open fun <Service> getService(serviceClass: Class<Service>, baseUrl: String): Service {
    return Retrofit.Builder()
                .client(client)
            .addConverterFactory(GsonConverterFactory.create(GsonFactory.getSingletonGson()))
//            .addConverterFactory(MoshiConverterFactory.create(moshi))
                .baseUrl(baseUrl)
                .build()
                .create(serviceClass)
    }
}
