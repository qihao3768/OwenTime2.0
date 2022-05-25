package com.example.owentime.base

import com.example.owentime.App
import com.example.owentime.BuildConfig
import com.franmontiel.persistentcookiejar.ClearableCookieJar
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit


abstract class BaseRetrofitClient {

    companion object CLIENT {
        private const val TIME_OUT = 5
    }

    private val client: OkHttpClient by lazy {
        val cookieJar: ClearableCookieJar =
            PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(App.getContext()))
        val builder = OkHttpClient.Builder()
                .addNetworkInterceptor(getHttpLoggingInterceptor())
                .connectTimeout(TIME_OUT.toLong(), TimeUnit.SECONDS)
            .cookieJar(cookieJar)
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

    private val json = Json {
        encodeDefaults = true
        coerceInputValues = true
        ignoreUnknownKeys = true
    }

    @OptIn(ExperimentalSerializationApi::class)
    open fun <Service> getService(serviceClass: Class<Service>, baseUrl: String): Service {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
                .client(client)
                .addConverterFactory(json.asConverterFactory(contentType))
                .baseUrl(baseUrl)
                .build()
                .create(serviceClass)
    }
}
