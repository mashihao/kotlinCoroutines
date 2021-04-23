package com.msh.kotlincoroutines

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Test

/**
 * @author : 马世豪
 * time : 4/23/21 16
 * email : ma_shihao@yeah.net
 * des :
 */
class Flow2 {


    @Test
    fun test() {


        runBlocking {
            flow {
                kotlinx.coroutines.delay(2000)
                flowOf(mutableListOf(1, 2, 3, 4, 5)).catch { emit(false) }.collect {

                    var bool = false
                    it.forEach {
                        if (it > 1) {
                            bool = true
                        }
                    }
                    emit(bool)
                }
            }.map {
                print(it)
                it
            }.collect {

            }
        }

    }
}