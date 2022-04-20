package com.msh.kotlincoroutines.honeywell

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.ToastUtils
import com.bumptech.glide.Glide
import com.msh.kotlincoroutines.databinding.ActivityHoneyWellBinding

class HoneyWellActivity : AppCompatActivity() {


//    lateinit var ip: EditText
//    lateinit var port: EditText
//    lateinit var connect: Button
//    lateinit var disconnect: Button
//    lateinit var print: Button

    lateinit var binding: ActivityHoneyWellBinding

    lateinit var printManager: PrintManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHoneyWellBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        AppSetting.savePrint_ip("192.168.7.107")
        AppSetting.savePrint_port(9100)

    }

    private fun initView() {
        printManager = PrintManager(this, mHandler)


        binding.connect.setOnClickListener {
            AppSetting.savePrint_ip(binding.ip.text.toString())
            AppSetting.savePrint_port(binding.port.text.toString().toInt())
            printManager.connect()
        }

        binding.disconnect.setOnClickListener {
            printManager.close()
        }

        binding.print.setOnClickListener {
            printManager.addQueue(LabelDetail("${binding.productName.text.toString()}"))


            val bitmap = PrintService.getBitmap(this, null, mHandler)

            Glide.with(this).load(bitmap)
                .into(binding.img)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        printManager.close()
    }
//    val LINK_SUCCESS = 10000 //链接成功
//
//    val LINK_FAILURE = 10001 //链接失败
//
//    val PRINT_FAILURE = 10002 //打印失败失败
//
//    val PRINT_ID_EMPTY = 10003 //没有打印机ip

    val mHandler = Handler {
        when (it.what) {
            ParamUtils.PRINT_ID_EMPTY -> {
                ToastUtils.showShort("请配置 IP 端口")
            }

            ParamUtils.PRINT_FAILURE -> {
                ToastUtils.showShort("打印失败")
            }

            ParamUtils.LINK_SUCCESS -> {
                ToastUtils.showShort("打印机连接成功")

            }
            ParamUtils.LINK_FAILURE -> {
                ToastUtils.showShort("打印机连接失败")
            }
            ParamUtils.PRINT_SUCCESS -> {
                ToastUtils.showShort("打印成功")
            }

            else -> {
                ToastUtils.showShort("未知错误")
            }


        }
        false
    }


}