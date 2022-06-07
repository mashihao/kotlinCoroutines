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
import java.math.RoundingMode
import java.text.DecimalFormat


suspend fun main() = coroutineScope {


    println("start")
    val num = 1.34567
    val df = DecimalFormat("#.###")
    df.roundingMode = RoundingMode.CEILING

    println(df.format(num))

    println("end")
//
//
//    listOf(1, 2, 3, 4, 5, 6).forEach {
//        if (it == 3) {
//            return@forEach
//        }
//        println(it)
//    }
//    listOf(1, 2, 3, 4, 5, 6).forEach loop@{
//        if (it == 3) {
//            return@loop
//        }
//        println(it)
//    }
//    run outside@{
//        listOf(1, 2, 3, 4, 5, 6).forEach {
//            if (it == 3) {
//                return@outside
//            }
//            println(it)
//        }
//    }

}

class FlowTest {

    @Test
    fun test() {


        println("start")
        val num = 1.34567
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.CEILING

        println(df.format(num))

        println("end")

        println()
        println()
        println()
        println()
        isHasUnStart()
    }

    private fun isHasUnStart(): Boolean {

        listOf(1, 2, 3, 4, 5, 6, 7).forEach {
            println(it)
            if (it == 3) {
                return true
            }
        }
        return false

    }

}



typealias ssss = (name: String, age: Int) -> Int