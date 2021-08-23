package com.msh.flow

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import org.junit.Test
import kotlin.math.sign

/**
 * @author : 马世豪
 * time : 2021/7/28 15
 * email : ma_shihao@yeah.net
 * des :
 */
class FlowTest {


    @DelicateCoroutinesApi
    @Test
    fun test() {

        val signEvent = MutableSharedFlow<String>()

        flow<String> {  }
            .filter {

            }

        runBlocking {
            async {
                runBlocking {
                    for (i in 0..13)
                    {
                        signEvent.tryEmit("$i")
                        delay(1000)
                    }
                }

                runBlocking {
                    signEvent.collect {
                        log(it)
                    }
                }
            }
        }


    }

    fun log(any: Any) {
        println("--->${any}")
    }

}