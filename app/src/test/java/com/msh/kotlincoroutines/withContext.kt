package com.msh.kotlincoroutines

import android.util.Log
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import org.junit.Test
import java.lang.RuntimeException

/**
 * @author : 马世豪
 * time : 5/13/21 15
 * email : ma_shihao@yeah.net
 * des :
 */
class withContext {


    @Test
    fun test() {
//        MutableLiveData


        runBlocking{


            flow {
                emit(123)
            }.map {
                if (it > 0) {
                    throw RuntimeException("RuntimeException")
                } else {
                    it
                }
            }.catch {
                log("${it}")
            }.onCompletion {
                log("onCompletion")
            }.collect {
                log("collect")
            }
        }

    }

    fun log(any: Any) {
        println(any)
    }
}

