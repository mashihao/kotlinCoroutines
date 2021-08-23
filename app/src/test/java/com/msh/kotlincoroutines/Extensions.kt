package com.msh.kotlincoroutines

import org.junit.Test

/**
 * @author : 马世豪
 * time : 5/14/21 14
 * email : ma_shihao@yeah.net
 * des :
 */
class Extensions {


    interface A {
        fun testA()
    }

    class B {
        fun testB() {
            println("B---B")
        }
    }

    inline fun B.testA() {
        println("A---A")
    }

    @Test
    fun test() {

    }

}