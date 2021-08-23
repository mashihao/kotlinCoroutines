package com.msh.kotlincoroutines

import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * @author : 马世豪
 * time : 5/13/21 15
 * email : ma_shihao@yeah.net
 * des :
 */


//模拟 lazy 方式
public  fun <T> msh(initializer:()->T):Msh<T> =MshImpl(initializer)

internal object UNINITIALIZED_VALUE
class MshImpl<out T>(initializer: () -> T) : Msh<T> {
    private var initializer: (() -> T)? = initializer
    @Volatile
    private var _value: Any? = UNINITIALIZED_VALUE
    override val value: T
        get() {
            val _v1 = _value
            if (_v1 !== UNINITIALIZED_VALUE) {
                @Suppress("UNCHECKED_CAST")
                return _v1 as T
            }
            return initializer!!()

        }

    override fun isInitialized(): Boolean {
        return _value !== UNINITIALIZED_VALUE
    }

}

public interface Msh<out T> :ReadOnlyProperty<Any,T> {

    // 数据持有
    public val value: T

    // 是否被实例过
    public fun isInitialized(): Boolean
    //获取数据
    override fun getValue(thisRef: Any, property: KProperty<*>): T {
        return value
    }
}

/**
 * Creates a new instance of the [Lazy] that is already initialized with the specified [value].
 */

/**
 * An extension to delegate a read-only property of type [T] to an instance of [Lazy].
 *
 * This extension allows to use instances of Lazy for property delegation:
 * `val property: String by lazy { initializer }`
 */

public inline operator fun <T> Msh<T>.getValue(thisRef: Any?, property: KProperty<*>): T = value