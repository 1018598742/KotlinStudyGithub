package com.fta.testdemo

import com.fta.testdemo.Bean.User
import com.fta.testdemo.network.entities.Person
import com.fta.testdemo.utils.ReflectUtils
import org.junit.Test

class TestReflect {

    @Test
    fun aboutReflect(){
        val person = Person()
        val user = User()
        user.name = "mike"
        user.age = 18
        user.longAge = 20
        user.integerAge = 22

        val toMap = ReflectUtils.toMap(person)
        println(toMap)
    }
}