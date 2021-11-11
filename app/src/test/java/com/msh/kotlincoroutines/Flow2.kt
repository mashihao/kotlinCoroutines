package com.msh.kotlincoroutines

import androidx.lifecycle.asLiveData
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import org.junit.Test
import java.lang.Exception
import java.lang.RuntimeException

/**
 * @author : 马世豪
 * time : 4/23/21 16
 * email : ma_shihao@yeah.net
 * des :
 */
class Flow2 {


    fun log(any: Any) {
        println("$any")
    }
    suspend fun fun1(): Int {
        log("fun1--->延时 2000")
        delay(2000)
        return 1
    }
    suspend fun fun2(num: Int): String {
        log("fun2---->延时 2000")
        delay(2000)
        return "fun2-->$num"
    }
    @Test
    fun test() {
        runBlocking {
            val int = fun1()
            log("最终输出结果${fun2(int)}")
//            fun1--->延时 2000
//            fun2---->延时 2000
//            最终输出结果fun2-->1
        }
    }

    val flow1 = flow{
        log("fun1--->延时 5000")
        delay(5000)
        emit(1)
    }
    fun flow2(int:Int) = flow{
        log("fun2---->延时 2000")
        delay(2000)
        emit( "fun2-->$int")
    }
    @FlowPreview
    @Test
    fun test2() {
        runBlocking {
//            var int :Int = 0
//            flow1.catch {
//                log(it)
//            }.collect {
//                int= it
//            }
//            flow2(int).collect {
//                log("最终输出结果$it")
//            }
            flow1.flatMapConcat {
                flow2(it)
            }.collect {
                log("最终输出结果$it")
            }
//            fun1--->延时 2000
//            fun2---->延时 2000
//            最终输出结果fun2-->1
        }
    }



}