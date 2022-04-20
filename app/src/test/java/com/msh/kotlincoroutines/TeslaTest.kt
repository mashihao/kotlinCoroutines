package com.msh.kotlincoroutines

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import org.junit.Test
import kotlin.system.measureTimeMillis

/**
 * @author : 马世豪
 * time : 4/14/21 13
 * email : ma_shihao@yeah.net
 * des :
 */
class TeslaTest {

    // 通过id 获取 新的
    suspend fun getTokenWithId(id: Int): String {
        delay(1000)
        return "new---${id}"
    }

    //从服务器获取 list 列表
    suspend fun getIdList() = flow {
        delay(1000)
        emit(mutableListOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10))
    }


    @Test
    fun test4() {
        listOf(1,2,3,4).forEachIndexed { index, i ->

            println("$index---->${i}")
        }

    }

    // 从服务器 获取 可用 ids  兑换新的 Token 输出Tesla2Test.kt
    @Test
    fun test() {

        var time = measureTimeMillis {
            runBlocking {
                println("showLoading")
                println()
                getIdList().map {
                    println("ids")
                    println(it)
                    it
                }.transform { list ->
                    var ttt = mutableListOf<Deferred<String>>()
                    list.forEach {
                        ttt.add(async { getTokenWithId(it) })
                    }
                    emit(ttt.awaitAll())
                }.map {
                    it
                }.collect {
                    println("Tokens")
                    println("---$it")
                }
                println()
                println()
                println("hideLoading")

            }
        }

        println("${time}")
    }

    suspend fun hello() {
        for (i in 0..100) {
            delay(1000)
            println("$i")

        }
    }

    @Test
    fun test2() {

        var people: People? = null

        println(people?.cam?.name ?: "123")
    }

    data class Cam(var name: String)


    data class People(var cam: Cam) {
    }
}



