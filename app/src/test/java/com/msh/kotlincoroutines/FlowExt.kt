package com.msh.kotlincoroutines

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
    private val collector: suspend (T) -> Unit
) : DefaultLifecycleObserver {

    private var job: Job? = null

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
    }


    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        job?.cancel()
        job = null
    }

    init {
        lifecycleOwner.lifecycle.addObserver(this)
        job = lifecycleOwner.lifecycleScope.launch {
            flow.collect {
                collector(it)
            }
        }
    }
}

inline fun <reified T> Flow<T>.observe(
    lifecycleOwner: LifecycleOwner,
    noinline collector: suspend (T) -> Unit
) {
    ObserverImpl(lifecycleOwner, this, collector)
}

inline fun <reified T> Flow<T>.observeIn(
    lifecycle: (Flow<T>) -> LifecycleObserver,
) {
    lifecycle(this)
}

inline fun <reified T> Flow<T>.observeIn(
    lifecycleOwner: LifecycleOwner
) {
    ObserverImpl(lifecycleOwner, this, {})
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
