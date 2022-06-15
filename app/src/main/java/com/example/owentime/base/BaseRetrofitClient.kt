package com.example.owentime.base

import com.example.owentime.BuildConfig

import com.example.owentime.jsonadapter.StringToObjAdapter
import com.example.owentime.net.Interceptor

import com.squareup.moshi.Moshi

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit


abstract class BaseRetrofitClient {

    companion object CLIENT {
        private const val TIME_OUT = 5
    }

    private val client: OkHttpClient by lazy {
//        val cookieJar: ClearableCookieJar =
//            PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(App.getContext()))
        val builder = OkHttpClient.Builder()
                .addNetworkInterceptor(getHttpLoggingInterceptor())
            .addInterceptor(Interceptor())
                .connectTimeout(TIME_OUT.toLong(), TimeUnit.SECONDS)
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
    val moshi = Moshi.Builder().add(StringToObjAdapter()).build()
    return Retrofit.Builder()
                .client(client)
//                .addConverterFactory(json.asConverterFactory(contentType))
//            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
                .baseUrl(baseUrl)
                .build()
                .create(serviceClass)
    }
}
