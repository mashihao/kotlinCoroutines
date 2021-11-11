package com.msh.kotlincoroutines

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.graphics.*
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.*
import com.blankj.utilcode.util.ToastUtils
import com.msh.kotlincoroutines.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import android.media.ThumbnailUtils

import androidx.core.graphics.drawable.DrawableCompat
import android.graphics.Bitmap

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.LogUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.msh.kotlincoroutines.base.BaseActivity
import com.msh.kotlincoroutines.databinding.MainItemBinding
import com.msh.kotlincoroutines.viewmodel.TestViewModelActivity


class MainActivity : BaseActivity() {

    val test: String by msh {
        "123"
    }

    val viewModel: MainViewModel by viewModels<MainViewModel>()

    lateinit var dataBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        dataBinding.lifecycleOwner = this
        dataBinding.viewmodel = viewModel
        init()
    }

    var adapter = Adapter()
    private fun init() {
        dataBinding.recycler.layoutManager = LinearLayoutManager(this)
        dataBinding.recycler.adapter = adapter

        adapter.setList(viewModel.list)

        adapter.setOnItemClickListener { adapter, view, position ->
            ActivityUtils.startActivity(viewModel.list[position].clazz)

        }
    }


    open class MainViewModel(application: Application) : AndroidViewModel(application) {
        var progress = MutableLiveData<Int>(1)


        var list = mutableListOf<Item>().apply {
            add(Item("ViewModelTest", TestViewModelActivity::class.java))
            add(Item("ViewModelTest", TestViewModelActivity::class.java))
            add(Item("ViewModelTest", TestViewModelActivity::class.java))
            add(Item("ViewModelTest", TestViewModelActivity::class.java))
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
