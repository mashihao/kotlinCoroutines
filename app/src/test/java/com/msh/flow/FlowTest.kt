package com.msh.flow

import android.os.Looper
import kotlinx.coroutines.*
import org.junit.Test
import kotlin.system.measureTimeMillis

/**
 * @author : 马世豪
 * time : 2021/7/28 15
 * email : ma_shihao@yeah.net
 * des :
 */


import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*


suspend fun main() = coroutineScope {

    listOf(1, 2, 3, 4, 5, 6).forEach {
        if (it == 3) {
            return@forEach
        }
        println(it)
    }
    listOf(1, 2, 3, 4, 5, 6).forEach loop@{
        if (it == 3) {
            return@loop
        }
        println(it)
    }
    run outside@{
        listOf(1, 2, 3, 4, 5, 6).forEach {
            if (it == 3) {
                return@outside
            }
            println(it)
        }
    }
}


typealias ssss = (name: String, age: Int) -> Int