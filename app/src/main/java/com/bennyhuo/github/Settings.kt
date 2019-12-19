package com.bennyhuo.github

import com.bennyhuo.github.common.Preference

object Settings {
    //代理
    var email: String by Preference(AppContext,"email","")
    var password: String by Preference(AppContext,"password","")
}