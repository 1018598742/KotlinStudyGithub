package com.bennyhuo.github.common

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun testBoolean() {
        val resultOtherwise = false.yes {
            1
        }.otherwise {
            2
        }

        val result = true.yes {
            3
        }.otherwise {
            4
        }

        val resultNo = false.no {
            5
        }.otherwise {
            6
        }


        assertEquals(resultOtherwise,2)
        assertEquals(result,3)
        assertEquals(resultNo,6)
//        println(resultOtherwise)
//        println(result)
    }
}
