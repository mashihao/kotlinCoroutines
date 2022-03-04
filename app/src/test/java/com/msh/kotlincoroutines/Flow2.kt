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

    val flow1 = flow {
        log("fun1--->延时 5000")
        delay(5000)
        emit(1)
    }

    fun flow2(int: Int) = flow {
        log("fun2---->延时 2000")
        delay(2000)
        emit("fun2-->$int")
    }

    @Test
    fun test2() {

        val str = "@fdsa http://www.baidu.com zhongjian 中间  http://www.xiaote.comjiewei @fdsa "
//        val regex =
//            "^([hH][tT]{2}[pP]:/*|[hH][tT]{2}[pP][sS]:/*|[fF][tT][pP]:/*)(([A-Za-z0-9-~]+).)+([A-Za-z0-9-~\\/])+(\\?{0,1}(([A-Za-z0-9-~]+\\={0,1})([A-Za-z0-9-~]*)\\&{0,1})*)$"
        var regexFindRes = Regex("""@[\S]*""").findAll(str)
        println("开始")
        regexFindRes.forEach {
            println("${it.value}")
        }
        println("结束")
    }


}