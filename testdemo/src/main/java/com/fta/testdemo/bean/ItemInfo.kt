package com.fta.testdemo.bean

data class ItemInfo(
    var url: String,
    var pkgName: String,//包名
    var status: Int = 0// 0：没有下载 1：下载中 2：暂停 3：完成
)