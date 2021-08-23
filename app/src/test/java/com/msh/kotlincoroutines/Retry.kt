package com.msh.kotlincoroutines

import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.runBlocking
import org.junit.Test
import java.lang.RuntimeException

/**
 * @author : 马世豪
 * time : 4/27/21 11
 * email : ma_shihao@yeah.net
 * des :
 */
class Retry {


    @Test
    fun test() {

        var i = 0
        runBlocking {


            flow {
                kotlinx.coroutines.delay(1000)
                ++i
                println(i)
                if (i == 10) {
                    emit(i)
                } else {
                    throw RuntimeException("i != 10")
                }

            }
                .retry {
                    println(it)
                    true
                }
                .collect {
                    println(i)
                }
        }
    }
}