package com.msh.kotlincoroutines

import android.app.Activity
import android.app.Application
import android.graphics.*
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.msh.kotlincoroutines.base.BaseActivity
import com.msh.kotlincoroutines.databinding.ActivityMainBinding
import com.msh.kotlincoroutines.databinding.MainItemBinding
import com.msh.kotlincoroutines.viewmodel.TestViewModelActivity
import com.youth.banner.Banner
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*


class MainActivity : BaseActivity() {

    val test: String by msh {
        "123"
    }

    var imageUrls = listOf(
        "https://img.zcool.cn/community/01b72057a7e0790000018c1bf4fce0.png",
        "https://img.zcool.cn/community/016a2256fb63006ac7257948f83349.jpg",
        "https://img.zcool.cn/community/01233056fb62fe32f875a9447400e1.jpg",
        "https://img.zcool.cn/community/01700557a7f42f0000018c1bd6eb23.jpg"

    )


    val viewModel: MainViewModel by viewModels<MainViewModel>()

    lateinit var dataBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        dataBinding.lifecycleOwner = this
        dataBinding.viewmodel = viewModel
        init()

        dataBinding.widgetSwitch2.setOnStateChangedListener(object :
            SwitchView.OnStateChangedListener {
            override fun toggleToOn(view: SwitchView?) {
                dataBinding.widgetSwitch2.toggleSwitch(true)
            }

            override fun toggleToOff(view: SwitchView?) {
                dataBinding.widgetSwitch2.toggleSwitch(false)
            }

        })

        val banner = dataBinding.bannerLayout2
        //设置图片加载器
        //设置图片加载器
        banner.setImageLoader(GlideImageLoader())
        //设置图片集合
        //设置图片集合
        banner.setImages(imageUrls)
        //banner设置方法全部调用完毕时最后调用
        //banner设置方法全部调用完毕时最后调用
        banner.start()


        val text = dataBinding.input.text.toString()
        ToastUtils.showShort("${if (text.isEmpty()) 0.0 else text.toDouble()}")


        dataBinding.input.filters = arrayOf(FloatNumInputFilter())

        dataBinding.contenttext.setOnClickListener {
            ToastUtils.showShort(dataBinding.input.text.toString())
            dataBinding.input.setText("0.23")
        }

        dataBinding.left.setOnClickListener {
            BannerUtils.previous(dataBinding.bannerLayout2)
        }
        dataBinding.right.setOnClickListener {
            BannerUtils.next(dataBinding.bannerLayout2)
        }


    }

    var adapter = Adapter()
    private fun init() {
        dataBinding.recycler.layoutManager = LinearLayoutManager(this)
        dataBinding.recycler.adapter = adapter

        adapter.setList(viewModel.list)

        adapter.setOnItemClickListener { adapter, view, position ->
            ActivityUtils.startActivity(viewModel.list[position].clazz)

        }

        viewModel.list2.observe(this) {
            Log.d("HomeViewModel", "observe size:${it.size}")
        }


        viewModel.loadData()

    }

    open class MainViewModel(application: Application) : AndroidViewModel(application) {
        var progress = MutableLiveData<Int>(1)


        var list = mutableListOf<Item>().apply {
            add(Item("ViewModelTest", TestViewModelActivity::class.java))
            add(Item("ViewModelTest", TestViewModelActivity::class.java))
            add(Item("ViewModelTest", TestViewModelActivity::class.java))
            add(Item("ViewModelTest", TestViewModelActivity::class.java))
        }


        //暴露给View层的列表数据
        val list2 = MutableLiveData<List<String?>>()

        //多个子Flow，这里简单都返回String，实际场景根据需要，返回相应的数据类型即可
        private val bannerFlow = MutableStateFlow<String?>(null)
        private val channelFlow = MutableStateFlow<String?>(null)
        private val listFlow = MutableStateFlow<String?>(null)


        init {
            //使用combine操作符
            viewModelScope.launch {
                combine(bannerFlow, channelFlow, listFlow) { bannerData, channelData, listData ->
                    Log.d(
                        "HomeViewModel",
                        "combine  bannerData:$bannerData,channelData:$channelData,listData:$listData"
                    )
                    //只要子flow里面的数据不为空，就放到resultList里面
                    val resultList = mutableListOf<String?>()
                    if (bannerData != null) {
                        resultList.add(bannerData)
                    }
                    if (channelData != null) {
                        resultList.add(channelData)
                    }
                    if (listData != null) {
                        resultList.add(listData)
                    }
                    resultList
                }.collect {
                    //收集combine之后的数据，修改liveData的值，通知UI层刷新列表
                    Log.d("HomeViewModel", "collect: ${it.size}")
                    list2.postValue(it)
                }
            }
        }

        fun loadData() {
            viewModelScope.launch(Dispatchers.IO) {
                //模拟耗时操作
                async {
                    delay(1000)
                    Log.d("HomeViewModel", "getBannerData success")
                    bannerFlow.emit("Banner")
                }
                async {
                    delay(2000)
                    Log.d("HomeViewModel", "getChannelData success")
                    channelFlow.emit("Channel")
                }
                async {
                    delay(3000)
                    Log.d("HomeViewModel", "getListData success")
                    listFlow.emit("List")
                }
            }
        }

    }

    data class Item(var title: String, var clazz: Class<out Activity>)


    class Adapter : BaseQuickAdapter<Item, BaseViewHolder>(R.layout.main_item, mutableListOf()) {
        init {
        }

        override fun convert(holder: BaseViewHolder, item: Item) {
            holder.bind<MainItemBinding>()?.let {
                it.executePendingBindings()
                it.desc.text = item.title


            }
        }

    }


}
