package com.xiaote

import kotlinx.coroutines.*
import org.junit.Test
import java.lang.Thread.sleep

class TaskTest {

    @Test
    fun testJob() {
        println("begin----")
        
        try {
            runBlocking {
                val jobs = this
                async {
                    println("开始了job1")

                    repeat(1000) {
                        delay(1000)
                        println("job1  --> $it")
//                        TODO 提前结束所有任务
                        if (it == 3) {
                            jobs.cancel(CancellationException("提前   ------ 取消了所有的子任务"))
                        }
                    }
                }
                async {
                    println("开始了job2")
                    repeat(2000) {
                        delay(1000)
                        println("job2  --> $it")
                    }
                }

                async {
                    try {
                        withTimeout(20000) {
                            println("=========")
                            repeat(1000) {
                                delay(1000)
                                println("time--->$it")
                            }
                        }
                    } catch (e: TimeoutCancellationException) {
                        println("超时处理")
                        jobs.cancel(CancellationException("超时 ---取消了所有的子任务"))
                    }
                }
            }
        } catch (e: CancellationException) {
            println("out------> 取消处理   $e")
        } catch (e: Exception) {
            println("out------>异常处理  $e")
        }
    }
}