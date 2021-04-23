package com.msh.kotlincoroutines

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)

        runBlocking {
            main()
        }
    }
    fun main() = runBlocking {
        val job1 = launch { // ①
            log(1)
            delay(1000) // ②
            log(2)
        }
        delay(100)
        log(3)
        job1.cancel() // ③
        log(4)
    }


    fun log(i :String){
        println(i)
    }
    fun log(i :Int){
        println(i)
    }

}