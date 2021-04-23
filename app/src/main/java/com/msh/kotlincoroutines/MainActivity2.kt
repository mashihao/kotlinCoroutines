package com.msh.kotlincoroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        main()
    }

    var name: String? = null
    fun main() = runBlocking {

        name?.let {
            println("${it}")
        } ?: {
            println("null")
        }
    }

    fun log(i: Int) {
        Log.e("MSH", "${i}")
    }

}