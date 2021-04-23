package com.msh.kotlincoroutines

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import java.lang.RuntimeException

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lifecycleScope.launch {
            main().catch {

            }.collect {

            }
        }
    }

    fun main() = flow {
        val job1 = lifecycleScope.launch { // ①
            log(1)
            try {
                delay(8000) // ②

            } catch (ex: Exception) {
                ex.printStackTrace()
                Log.e("MSH", "-----${ex}")
            }
        }
        delay(6000)
        log(3)
        job1.cancel() // ③
        log(4)
        emit(true)
    }


    var name: String? = null


    fun log(i: Int) {
        Log.e("MSH", "${i}")
    }

}