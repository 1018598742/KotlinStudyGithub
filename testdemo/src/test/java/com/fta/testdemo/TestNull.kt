package com.fta.testdemo

import android.util.Log
import org.junit.Test

class TestNull {

    @Test
    fun aboutIsNull() {
        var abc: String? = "hello"
        abc?.let {
            System.out.println("is not null")
        } ?: run {
            System.out.println("is null")
        }
    }
}