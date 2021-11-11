package com.msh.kotlincoroutines

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.lang.Runnable
import java.lang.RuntimeException


class byLazyTest : AppCompatActivity() {

    val test: String by msh {
        "123"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        Log.e("MSH---", Thread.currentThread().name)

    }

    fun log(any: Any) {
        Log.e("MSH---", "$any")
    }

    private fun logThread() {
        log(Thread.currentThread())
    }


}