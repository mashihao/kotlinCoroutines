package com.msh.kotlincoroutines

import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.launch
import java.io.IOException

@PublishedApi
internal class ObserverImpl<T>(
    lifecycleOwner: LifecycleOwner,
    private val flow: Flow<T>,
    private val collector: suspend (T) -> Unit,
    private val error: suspend (Throwable) -> Unit
) : DefaultLifecycleObserver {

    private var job: Job? = null

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        Log.e("MSH", "onStart")
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        Log.e("MSH", "onStop")
    }

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        Log.e("MSH", "onResume")
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        Log.e("MSH", "onDestroy")
        job?.cancel()
        job = null
    }

    init {
        lifecycleOwner.lifecycle.addObserver(this)
        job = lifecycleOwner.lifecycleScope.launch {
            flow.catch {
                error.invoke(it)

            }.collect {
                collector(it)
            }
        }
    }

    fun cancel() {
        try {
            job?.cancel()
            job = null
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}


inline fun <reified T> Flow<T>.observeIn(
    lifecycle: (Flow<T>) -> LifecycleObserver,
) {
    lifecycle(this)
}


fun <T> Flow<T>.handleError(print: Boolean = false): Flow<T> = catch { cause ->
    if (print) {
        cause.printStackTrace()
    }
}

fun <T> Flow<T>.retryWhenIOException(retries: Long) = retry(retries) { cause ->
    cause is IOException
}


inline fun <reified T, R> Collection<Flow<T>>.combine(crossinline transform: suspend (Array<T>) -> R): Flow<R> {
    return kotlinx.coroutines.flow.combine(this, transform)
}
