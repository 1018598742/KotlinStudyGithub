package com.fta.testdemo.utils

import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.*


class SSLSocketFactoryUtils {
    companion object {

        @JvmStatic
        fun createSSLSocketFactory(): SSLSocketFactory {
            val sslContext = SSLContext.getInstance("TLS")
            sslContext.init(null, arrayOf(createTrustAllManager()), SecureRandom())
            return sslContext.socketFactory
        }

        @JvmStatic
        fun createTrustAllManager(): X509TrustManager = object : X509TrustManager {
            override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {
                //do nothing，接受任意客户端证书
            }

            override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {
                //do nothing，接受任意服务端证书
            }

            override fun getAcceptedIssuers(): Array<X509Certificate> {
                return emptyArray()
            }
        }

        @JvmStatic
        fun trustAllHostnameVerifier(): HostnameVerifier = object : HostnameVerifier {
            override fun verify(hostname: String?, session: SSLSession?): Boolean = true
        }

    }
}