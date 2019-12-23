package com.bennyhuo.github.settings

import com.bennyhuo.github.AppContext
import com.bennyhuo.github.common.sharedpreferences.Preference

object Settings {
    //代理
    var email: String by Preference(
        AppContext,
        "email",
        ""
    )
    var password: String by Preference(
        AppContext,
        "password",
        ""
    )
}