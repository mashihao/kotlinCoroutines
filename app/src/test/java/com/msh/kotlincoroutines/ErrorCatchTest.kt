package com.msh.kotlincoroutines

import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ErrorCatchTest {

    @Test
    fun testFlowError() {
        runBlocking {

            var f = flow {
                try {
                    throw Exception("error")
                    emit(1)
                } catch (e: Exception) {
                    println(e.message)
//                    emit(e)
                    error(e)
                }
            }

            var f2 = flow {
                try {
                    throw Exception("error")
                    emit(1)
                } catch (e: Exception) {
                    println(e.message)
//                    emit(e)
                    error(e)
                }
            }
            println()
            println()
            println()
            println("@ 带了自己拦截的===================================")
            f.catch {
                println("提前拦截")
            }.collect {
                print("over$it")
            }
            println()
            println()
            println()
            println("@ 自己不带拦截的===================================")
            f2.collect() {

            }
            f2.collect {
                println("over$it")
            }
        }
    }
}




//
//@OptIn(InternalCoroutinesApi::class)
//public suspend inline fun <T> Flow<T>.collect(crossinline action: suspend (value: T) -> Unit) =
//    try {
//        this.collect(object : FlowCollector<T> {
//            override suspend fun emit(value: T) = action(value)
//        })
//    } catch (e: Exception) {
//        println("Default Error  :" + e.message)
//    }
