package com.msh.kotlincoroutines.honeywell

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.IBinder
import com.blankj.utilcode.util.LogUtils

/**
 * @author : 马世豪
 * time : 2022/4/22 09
 * email : ma_shihao@yeah.net
 * des : 打印服务
 */
class PrinterService : Service() {
    companion object {
        val TAG = "PrinterService"

        val ACTION = "com.action.printer"

        public fun startCommand(context: Context, any: LabelDetail) {

            val intent = Intent(context, PrinterService.javaClass)
            intent.action = ACTION
            intent.putExtra("data", any)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(intent)
            } else {
                context.startService(intent)
            }
        }
    }


    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        LogUtils.eTag(TAG, "服务启动")

        //初始化 PrintManager
        PrintManager.getInstance().init(applicationContext, Handler())
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {


        //如果是打印的action
        if (ACTION == intent?.action ?: "") {
            LogUtils.eTag(TAG, "接收到action")
            val data = intent?.getSerializableExtra("data") as LabelDetail
            LogUtils.eTag(TAG, "接收到数据${data}")
            PrintManager.getInstance().addQueue(data)
        }

        return START_NOT_STICKY
    }





}