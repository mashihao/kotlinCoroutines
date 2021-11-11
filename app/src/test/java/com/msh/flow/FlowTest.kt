package com.msh.flow

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import org.junit.Test
import java.util.*
import kotlin.math.sign

/**
 * @author : 马世豪
 * time : 2021/7/28 15
 * email : ma_shihao@yeah.net
 * des :
 */
class FlowTest {


    fun treadName(): String {
        return Thread.currentThread().name
    }

    @DelicateCoroutinesApi
    @Test
    fun test() {



        val listA = mutableListOf<Int>(1,2,3,4,5)
        val listB = listA.filter {
            it>2
        }.map {
            "listb--->$it"
        }.toMutableList()
        log(listB)

    }

    fun log(any: Any) {
        println("--->${any}")
    }

}