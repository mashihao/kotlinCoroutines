package com.example.banner

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson


class MainActivity : AppCompatActivity() {
    private var recyclerView: AutoPollRecyclerView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        initData()
        initListener()
    }

    data class Test(var createdAt:Long)

    private fun initView() {
        recyclerView = findViewById(R.id.recyclerview)
        recyclerView?.setLayoutManager(
            LinearLayoutManager(
                this,
                LinearLayoutManager.VERTICAL,
                false
            )
        ) //设置LinearLayoutManager.HORIZONTAL  则水平滚动

        findViewById<ImageView>(R.id.img).setOnClickListener {



//            <data
//            android:scheme="scheme_byd"
//            android:host="host_com_byd_aeri_caranywhere"
//            android:path="/path_jump_navi" />

            startActivity(Intent(this,SecondActivity::class.java))

        }
    }

    private fun initData() {
        val reponse = "{\n" +
                "    \"datas\": [\n" +
                "        {\n" +
                "            \"giftName\": \"1\",\n" +
                "            \"phone\": \"****\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"giftName\": \"2\",\n" +
                "            \"phone\": \"****\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"giftName\": \"3\",\n" +
                "            \"phone\": \"****\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"giftName\": \"4\",\n" +
                "            \"phone\": \"****\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"giftName\": \"5\",\n" +
                "            \"phone\": \"****\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"giftName\": \"6\",\n" +
                "            \"phone\": \"****\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"giftName\": \"7\",\n" +
                "            \"phone\": \"****\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"giftName\": \"8\",\n" +
                "            \"phone\": \"****\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"giftName\": \"9\",\n" +
                "            \"phone\": \"****\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"giftName\": \"10\",\n" +
                "            \"phone\": \"****\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"giftName\": \"11\",\n" +
                "            \"phone\": \"****\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"giftName\": \"12\",\n" +
                "            \"phone\": \"****\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"giftName\": \"13\",\n" +
                "            \"phone\": \"****\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"giftName\": \"14\",\n" +
                "            \"phone\": \"****\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"giftName\": \"15\",\n" +
                "            \"phone\": \"****\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"giftName\": \"16\",\n" +
                "            \"phone\": \"****\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"giftName\": \"17\",\n" +
                "            \"phone\": \"****\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"giftName\": \"18\",\n" +
                "            \"phone\": \"****\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"giftName\": \"19\",\n" +
                "            \"phone\": \"****\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"giftName\": \"20\",\n" +
                "            \"phone\": \"****\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"msg\": \"success\",\n" +
                "    \"ret\": 0\n" +
                "}"
        val autoScrollLuckyListReq: AutoScrollLuckyListReq = Gson().fromJson(
            reponse,
            AutoScrollLuckyListReq::class.java
        )
        //0为请求成功
        if (autoScrollLuckyListReq.ret === 0) {
            val autoPollAdapter =
                AutoPollAdapter(applicationContext, autoScrollLuckyListReq.datas)
            recyclerView!!.adapter = autoPollAdapter
            //启动滚动
            recyclerView!!.start()
        }
    }

    private fun initListener() {}
}
