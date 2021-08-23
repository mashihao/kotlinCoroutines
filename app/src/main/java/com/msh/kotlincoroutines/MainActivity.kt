package com.msh.kotlincoroutines

import android.app.Activity
import android.app.Application
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.*
import com.google.android.material.dialog.MaterialDialogs
import com.msh.kotlincoroutines.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*


class MainActivity : AppCompatActivity() {

    val test: String by msh {
        "123"
    }

    var viewModel: MainViewModel? = null

    var observerFroever = MutableLiveData("123")
    lateinit var dataBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        startActivity(Intent(this,MainActivity2::class.java))
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        val launch111 = registerForActivityResult(
            CommunityDetailActivityResultContract()
        ) {

        }
        dataBinding.viewmodel = viewModel
        dataBinding.executePendingBindings()

        lifecycleScope.launch {


            for (i in 0..100) {

                delay(1000)
                Log.e("MSH---", "${(0 until 100).random()}")
                viewModel?.progress?.value = i
            }

        }

        viewModel?.progress?.observe(this)
        {
            dataBinding.linearDeterminate2.progress = it
        }


        observerFroever.observeForever {
            showToast(it)
        }

        var button = findViewById<Button>(R.id.btn_login)
        button.setOnClickListener {

            AlertDialog.Builder(this).setTitle("helloword").create().show()
        }
        Thread(object : Runnable {
            override fun run() {

                try {
                    Thread.sleep(10000)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
                Log.e("MSH---", Thread.currentThread().name)

                button.text = "123"
            }
        }).start()

        lifecycleScope.launch(Dispatchers.IO) {
            delay(3000)
            button.text = "456"
            log(Thread.currentThread().name)
            flow {
                log(Thread.currentThread().name)
                log("start")
                delay(1000)
                emit(1)
            }.flowOn(Dispatchers.Main).collect {
                log(Thread.currentThread().name)
                button.text = "789"
            }
        }


        var charSequence: CharSequence
        var content = "<p>测试测试测试测试测试测试测试</p ><p>测试 测试测试测<span style=\"color: rgb(77,\n" +
                "      128,\n" +
                "      191);\">试测试测试测试</span></p ><p><span style=\"color: rgb(194,\n" +
                "      79,\n" +
                "      74);\">测试测试测试</span></p ><p><span style=\"font-weight: bold;\"><span style=\"color: rgb(194,\n" +
                "      79,\n" +
                "      74);\">加粗</span>加粗加粗加粗加粗加粗</span></p ><p><span style=\"color: rgb(249,\n" +
                "      150,\n" +
                "      59);\">测试测试测试</span></p ><p>测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试select</p >";
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
//            charSequence = Html.fromHtml(content, Html.FROM_HTML_MODE_COMPACT);
//        } else {
//            charSequence = Html.fromHtml(content);
//        }
//        dataBinding.text.setHtml(content)
    }

    fun log(any: Any) {
        Log.e("MSH---", "$any")
    }

    private fun logThread() {
        log(Thread.currentThread())
    }


    open class CommunityDetailActivityResultContract :
        ActivityResultContract<String, String>() {

        override fun createIntent(context: Context, input: String): Intent {
            input?.let {
                return Intent(context, MainActivity::class.java).apply {
                }
            }
        }

        override fun parseResult(resultCode: Int, intent: Intent?): String? {

            if (resultCode == Activity.RESULT_OK) {
                return "123"
            }
            return null
        }

    }


    class MainViewModel(application: Application) : AndroidViewModel(application) {
        var progress = MutableLiveData<Int>()
    }

    fun Any.showToast(msg: String) {
        Toast.makeText(this@MainActivity, msg, Toast.LENGTH_SHORT).show()
    }
}
