package com.msh.kotlincoroutines

import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.TimeUtils
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import org.junit.Test
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * @author : 马世豪
 * time : 2021/6/24 17
 * email : ma_shihao@yeah.net
 * des :
 */
class combine {

    @OptIn(InternalCoroutinesApi::class)
    suspend inline fun <T> Flow<T>.xtCollect(crossinline action: suspend (value: T) -> Unit) =
        this.catch {
            println("@AppError: 默认的xtCollect异常拦截 ${it.message}")

        }.collect(object : FlowCollector<T> {
            override suspend fun emit(value: T) = action(value)
        })
    /**
     * Flow 的中间操作符filter map
     * Sequence也可以调用filter map等方法
     * Flow与Sequence之间的区别：对于Flow来说 中间运算符代码内可以调用挂起函数(对比Kotlin2)
     */
    private suspend fun myExecution(input: Int): String {
        println("myExecution: $input")
        delay(500)
        return "output: $input"
    }

    @Test
    fun main() = runBlocking<Unit> {
        // 将1到10转换为flow 过滤其中大于5的数字 并将这些数字形成一个映射 最后将其打印出来
        (1..10).asFlow().filter { it > 5 }.map { myExecution(it) }// 这里中间函数map调用了挂起函数
            .collect { println(it) }
    }

    class HelloKotlin8 {
    }

    class Person(val name: String, var age: Int)


}