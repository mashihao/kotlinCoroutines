package com.xiaote

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import org.junit.Test
import java.lang.RuntimeException

/**
 * @author : 马世豪
 * time : 4/14/21 13
 * email : ma_shihao@yeah.net
 * des :
 */
class Tesla3Test {

    data class Tesla(
        var id: Int,
        var token: String? = null,
        var url: String? = null
    )


    @Test
    fun getTeslaInfo() {
        runBlocking {

            var time = System.currentTimeMillis() / 1000
            println("show loading  $time")
            delay(2000)
            var teslaInfo = mutableListOf<Tesla>()
            for (i in 1..3) {
                teslaInfo.add(Tesla(id = i))
            }
            println("testList init over!")
//            var teslaMap: Map<Int, Tesla> = teslaInfo.associateBy { it.id }
            //开始获取
            println("====begin get teslaInfoUrl")
            var jobList = mutableListOf<Deferred<Any>>()
            runBlocking {
                teslaInfo.forEach {
                    jobList.add(async {
                        getToken(it).collectNext {
                            getUrl(it, 1)
                        }.collect {
                            println("你好啊")
                        }
                    })
                }
                jobList.forEach { it.await() }
            }

            println("All Data : $teslaInfo")
            //获取到的所有数据
            println("showOver : ${(System.currentTimeMillis() / 1000) - time}")
        }
    }


    fun getToken(tesla: Tesla) = flow<Tesla> {
//        delay((500..3000).random().toLong())
        delay(1000)
        tesla.token = "token ${(1..10000).random()}"
        emit(tesla)
    }

    var i = 0

    fun getUrl(tesla: Tesla, count: Int) = flow {
        println("get URL")
//        delay((500..3000).random().toLong())
        delay(1000)
        tesla.url = "$count url ${(1..10000).random()}"
        ++i
        if (i >= 10) {
            emit(tesla)
        } else {
            println("${i}---i!=10")
            throw RuntimeException("i!=10")
        }
    }.retry {
        true
    }

}

//一个个的flow，避免嵌套
public suspend inline fun <T> Flow<T>.collectNext(crossinline action: suspend (value: T) -> Flow<T>): Flow<T> {
    var next: T? = null
    this.collect {
        next = it
    }
    return next?.let { action(it) } ?: emptyFlow()
}

