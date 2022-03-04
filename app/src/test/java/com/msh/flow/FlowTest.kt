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


suspend fun main() = coroutineScope{
    launch{
        delay(1000)
        println("kotlin coroutines world")
    }
    println("hello word")
}


typealias ssss = (name: String, age: Int) -> Int