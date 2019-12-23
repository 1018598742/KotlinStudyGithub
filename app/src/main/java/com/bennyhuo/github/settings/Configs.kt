package com.bennyhuo.github.settings

import android.util.Log
import com.bennyhuo.github.AppContext
import com.bennyhuo.github.common.log.logger
import com.bennyhuo.github.utils.deviceId

object Configs {

    object Account{
        val SCOPES = listOf("user", "repo", "notifications", "gist", "admin:org")
        const val clientId = "d057a3badf651b827dc4"
        const val clientSecret = "7424c800acf02a9b11addd02be7db4f828db8fb8"
        const val note = "kotliner.cn"
        const val noteUrl = "http://www.kotliner.cn"

        val fingerPrint by lazy {
            (AppContext.deviceId + clientId).also { logger.info("fingerPrint: "+it)
                Log.i("GitHub","fingerPrint:${it}")
            }
        }
    }

}