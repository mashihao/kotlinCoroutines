package com.msh.kotlincoroutines

import android.util.Log
import org.junit.Test
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.lang.Exception
import java.lang.RuntimeException
import kotlin.system.measureTimeMillis

/**
 * @author : 马世豪
 * time : 4/26/21 13
 * email : ma_shihao@yeah.net
 * des : DefaultLifecycleObserverTest  测试 生命周期
 */
class DefaultLifecycleObserverTest {


    suspend fun test1():String{
        delay(10000)
        println("test1")
        return "test1"
    }
    suspend fun test2():String{
        delay(10000)
        println("test2")
        return "testl2"
    }

    @Test
    fun test() {

        runBlocking {
            async {
                try {

                    withTimeout(11000)
                    {
                        test1()
                        test2()
                    }
                }catch (ex:Exception)
                {
                    print("jieshule")
                }
            }
        }


    }

    fun log(any: Any) {
        println(any)
    }
}