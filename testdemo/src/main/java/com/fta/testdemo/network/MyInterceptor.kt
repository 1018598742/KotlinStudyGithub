package com.fta.testdemo.network

import okhttp3.Interceptor
import okhttp3.Response

class MyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        request = request.newBuilder()
            .addHeader("sid", "27c08e62233ecc6070b5899993e22063eb62c6174881a89f95abbe033129f407688d8bc24f175638fb777beffe337fff6064756573af4ae2053bf667ddbd7cc3")
            .build()
        return chain.proceed(request)
    }

}