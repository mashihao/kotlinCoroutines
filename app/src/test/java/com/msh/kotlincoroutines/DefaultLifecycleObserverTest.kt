package com.msh.kotlincoroutines

import android.util.Log
import org.junit.Test
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import okhttp3.internal.wait
import java.lang.Exception
import java.lang.RuntimeException
import java.lang.Thread.sleep
import kotlin.system.measureTimeMillis

/**
 * @author : 马世豪
 * time : 4/26/21 13
 * email : ma_shihao@yeah.net
 * des : DefaultLifecycleObserverTest  测试 生命周期
 */
class DefaultLifecycleObserverTest {


    suspend fun test1(): String {
        delay(10000)
        println("test1")
        return "test1"
    }

    suspend fun test2(): String {
        delay(10000)
        println("test2")
        return "testl2"
    }

    @Test
    fun test() {

        for (i in 0..10) {
            println(i)
        }

    }

    fun log(any: Any) {
        println(any)
    }

    val task1: () -> String = {
        println("task1 start")
        sleep(2000)
        "Hello".also { println("task1 finished: $it") }
    }

    val task2: () -> String = {
        println("task2 start")
        sleep(4000)
        "World".also { println("task2 finished: $it") }
    }

    val task3: (String, String) -> String = { p1, p2 ->
        println("task3 start")
        sleep(2000)
        "$p1 $p2".also { println("task3 finished: $it") }
    }


    @Test
    fun testAsync() {

        runBlocking {
            val one = async {
                var i = 0;
                while (i<10){
                    println("one:$i")
                    i++
                    delay(500L)
                }
            }.await()

            val two = async {
                var j = 0;
                while (j<10){
                    println("two:$j")
                    j++
                    delay(500L)
                }
            }.await()
        }
        println("看下线程有没有阻塞。")
    }

    @Test
    fun testCoroutine() {
        runBlocking {
            val c1 = async(Dispatchers.IO) {
                task1()
            }
            val c2 = async(Dispatchers.IO) {
                task2()
            }
            task3(c1.await(), c2.await())
        }
    }

    @Test
    fun testFlow() {
        val flow1 = flow<String> { emit(task1()) }
        val flow2 = flow<String> { emit(task2()) }

        runBlocking {

            flow1.zip(flow2) { t1, t2 ->
                task3(t1, t2)
            }.flowOn(Dispatchers.IO)
                .collect {
                    print("success")
                }
        }
    }

    @Test
    fun test_join() {

        lateinit var s1: String
        lateinit var s2: String
        val t1 = Thread {
            s1 = task1()
        }
        val t2 = Thread {
            s2 = task2()
        }

        t1.start()
        t2.start()
        t1.join()
        t2.join()
        task3(s1, s2)
    }
}