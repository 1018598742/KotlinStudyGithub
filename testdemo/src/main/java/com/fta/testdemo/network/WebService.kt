package com.fta.testdemo.network

import com.bennyhuo.github.common.ext.ensureDir
import com.fta.testdemo.AppContext
import com.fta.testdemo.utils.SSLSocketFactoryUtils
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory2
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

private const val BASE_URL = "https://192.168.0.92:8443"
//private const val BASE_URL = "http://192.168.0.92:8080"

private val cacheFile by lazy {
    File(AppContext.cacheDir, "webservice").apply { ensureDir() }
}

val retrofit by lazy {
    Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(
            RxJava2CallAdapterFactory2.createWithSchedulers(
                Schedulers.io(),
                AndroidSchedulers.mainThread()
            )
        )
        .client(
            OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .cache(Cache(cacheFile, 1024 * 1024 * 1024))
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .sslSocketFactory(
                    SSLSocketFactoryUtils.createSSLSocketFactory(),
                    SSLSocketFactoryUtils.createTrustAllManager()
                )
                .hostnameVerifier(SSLSocketFactoryUtils.trustAllHostnameVerifier())
                .build()
        )
        .baseUrl(BASE_URL)
        .build()
}

