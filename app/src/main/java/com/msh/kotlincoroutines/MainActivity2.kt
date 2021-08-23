package com.msh.kotlincoroutines

import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import kotlin.system.measureTimeMillis

class MainActivity2 : AppCompatActivity() {

    val TAG = "MSH222222--------"
    val scope = MainScope()

    @DelicateCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        findViewById<Button>(R.id.btn_login).setOnClickListener {


            var job:Job?=null
            job = lifecycleScope.launch {


                var s = 0
                try {
                    withTimeout(20000)
                    {
                        repeat(1000) { i ->
                            ++s
                            LogUtils.eTag("wakeup", "$i s")
                            delay(1000L)
                            if (s == 10) {
                            }
                        }
                    }
                } catch (ex: Exception) {
                    ex.printStackTrace()
                    LogUtils.eTag("wakeup", "$ex s")
                }

            }

        }


        lifecycleScope.launch {

            val channel = Channel
            log(Thread.currentThread().name)
        }


        lifecycleScope.launch(Dispatchers.Main) {
            log("Dispatchers.Main----->" + Thread.currentThread().name)
        }

        // 冷流 有序 协作取消
        //自由的
        lifecycleScope.launch(Dispatchers.Unconfined) {
            log("Dispatchers.Unconfined----->" + Thread.currentThread().name)
        }


        lifecycleScope.launch(Dispatchers.IO) {
            log("Dispatchers.IO----->" + Thread.currentThread().name)
        }


        lifecycleScope.launch(Dispatchers.Default) {
            log("Dispatchers.Default----->" + Thread.currentThread().name)
        }

        val job = GlobalScope.launch {
            val a = async {
                1 + 2
            }

            val b = async {
                1 + 3
            }

            val c = a.await() + b.await()
            Log.e(TAG, "result:$c")
        }


    }





    private suspend fun getResult(num: Int): Int {
        delay(5000)
        return num * num
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
    }

    fun log(any: Any) {
        Log.e("MSH22222222------", "$any")
    }

}    // 构建输入框文字变化流
fun EditText.textChangeFlow(): Flow<Editable> = callbackFlow {

    // 构建输入框监听器
    val watcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {}
        override fun beforeTextChanged( s: CharSequence?, start: Int, count: Int, after: Int ) { }

        // 在文本变化后向流发射数据
        override fun onTextChanged( s: CharSequence?, start: Int, before: Int, count: Int ) {
            s?.let { offer(it) }
        }
    }
    addTextChangedListener(watcher) // 设置输入框监听器
    awaitClose { removeTextChangedListener(watcher) } // 阻塞以保证流一直运行
} as Flow<Editable>