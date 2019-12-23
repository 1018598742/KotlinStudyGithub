package com.fta.testdemo.network.entities

data class Result<T>(
    var status : Int,
    var msg:String,
    var content:T
)