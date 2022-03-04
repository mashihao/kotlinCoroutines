package com.msh.kotlincoroutines

import android.os.*
import android.view.View
import android.widget.Button
import androidx.annotation.Nullable
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.msh.kotlincoroutines.base.BaseActivity
import java.lang.Math.log
import java.lang.reflect.Method


class HandlerActivity : BaseActivity() {
    private lateinit var button1: Button
    private lateinit var button2: Button
    private lateinit var button3: Button
    private lateinit var button4: Button
    private var token = 0
    private lateinit var mHandler: Handler
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_handler)
        initView()
        initHandler()
    }

    private fun initHandler() {
        Thread {
            Looper.prepare()
            mHandler = object : Handler() {
                override fun handleMessage(msg: Message) {
                    //super.handleMessage(msg);
                    if (msg.what === MESSAGE_TYPE_SYNC) {
                        log( "收到普通消息")
                    } else if (msg.what === MESSAGE_TYPE_ASYN) {
                        log( "收到异步消息")
                    }
                }
            }
            Looper.loop()
        }.start()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun initView() {
        button1 = findViewById(R.id.send_syne)
        button2 = findViewById(R.id.remove_sunc)
        button3 = findViewById(R.id.send_message)
        button4 = findViewById(R.id.send_async_message)
        button1.setOnClickListener { sendSyncBarrier() }
        button2.setOnClickListener { removeSyncBarrier() }
        button3.setOnClickListener { sendSyncMessage() }
        button4.setOnClickListener { sendAsynMessage() }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    private fun sendAsynMessage() {
        log( "插入异步消息")
        val message: Message = Message.obtain()
        message.what = MESSAGE_TYPE_ASYN
        message.setAsynchronous(true) //3
        mHandler.sendMessageDelayed(message, 1000)
    }

    private fun sendSyncMessage() {
        log( "插入普通消息")
        val message: Message = Message.obtain()
        message.what = MESSAGE_TYPE_SYNC
        mHandler.sendMessageDelayed(message, 1000)
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private fun removeSyncBarrier() {
        try {
            log( "移除屏障")
            val queue: MessageQueue = mHandler.getLooper().getQueue()
            val method: Method =
                MessageQueue::class.java.getDeclaredMethod(
                    "removeSyncBarrier",
                    Int::class.javaPrimitiveType
                )
            method.setAccessible(true)
            method.invoke(queue, token) //2
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 插入同步屏障
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    private fun sendSyncBarrier() {
        try {
            log( "插入同步屏障")
            val queue: MessageQueue = mHandler.getLooper().getQueue()
            val method: Method = MessageQueue::class.java.getDeclaredMethod("postSyncBarrier")
            method.isAccessible = true
            token = method.invoke(queue) as Int //1
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    companion object {
        const val MESSAGE_TYPE_SYNC = 1
        const val MESSAGE_TYPE_ASYN = 2
    }
}
