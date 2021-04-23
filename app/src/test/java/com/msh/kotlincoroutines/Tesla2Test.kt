package com.xiaote

import android.util.Log
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import org.junit.Test
import kotlin.system.measureTimeMillis
import kotlin.time.measureTime

/**
 * @author : 马世豪
 * time : 4/14/21 13
 * email : ma_shihao@yeah.net
 * des :
 */
class Tesla2Test {

    data class Tesla(
        var id: Int,
        var token: String? = null,
        var url: String? = null
    )


    @Test
    fun getTeslaInfo() {

        var time = measureTimeMillis {
            runBlocking {

                println("show loading ")
                delay(2000)
                var teslaInfo = mutableListOf<Tesla>()
                for (i in 1..30) {
                    teslaInfo.add(Tesla(id = i))
                }
                println("testList init over!")
//            var teslaMap: Map<Int, Tesla> = teslaInfo.associateBy { it.id }
                //开始获取
                println("====begin get teslaInfoUrl")

                var ttt = mutableListOf<Deferred<Any>>()

                teslaInfo.forEach {
                    ttt.add(async {
                        getToken(it).transform { token ->
                            getUrl(token).collect {
                                emit(it)
                            }
                        }.collect()
                    })
                }
                ttt.awaitAll()

                println("All Data : $teslaInfo")
                //获取到的所有数据

            }
        }
        println("showOver : ${time}")

    }

    fun getToken(tesla: Tesla) = flow<Tesla> {
//        delay((500..3000).random().toLong())
        delay(1000)
        tesla.token = "token ${(1..10000).random()}"
        println("getToken --${tesla.token}")
        emit(tesla)
    }

    fun getUrl(tesla: Tesla) = flow {
//        delay((500..3000).random().toLong())
        delay(1000)
        tesla.url = "url ${(1..10000).random()}"
        println("getUrl --${tesla.url}")
        emit(tesla)
    }

}



