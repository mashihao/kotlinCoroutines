package com.msh.kotlincoroutines

import org.jetbrains.annotations.TestOnly
import org.junit.Test
import kotlin.properties.ReadOnlyProperty
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * @author : 马世豪
 * time : 5/13/21 13
 * email : ma_shihao@yeah.net
 * des :
 */
class DelegateTest {

    // 约束类
    interface IGamePlayer {
        // 打排位赛
        fun rank()

        // 升级
        fun upgrade()
    }

    // 被委托对象，本场景中的游戏代练
    class RealGamePlayer(private val name: String) : IGamePlayer {
        override fun rank() {
            println("$name 开始排位赛")
        }

        override fun upgrade() {
            println("$name 升级了")
        }

    }


    // 委托对象
    class DelegateGamePlayer(private val player: IGamePlayer) : IGamePlayer by player
    class Delegate {
        operator fun getValue(thisRef: Any?, property: KProperty<*>): String {
            return "$thisRef, thank you for delegating '${property.name}' to me!"
        }

        operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
            println("$value has been assigned to '${property.name}' in $thisRef.")
        }
    }

    // val 属性委托实现
    class Delegate1 : ReadOnlyProperty<Any, String> {
        override fun getValue(thisRef: Any, property: KProperty<*>): String {
            return "${thisRef}--通过实现ReadOnlyProperty实现，name:${property.name}"
        }
    }

    // var 属性委托实现
    class Delegate2 : ReadWriteProperty<Any, Int> {
        override fun getValue(thisRef: Any, property: KProperty<*>): Int {
            return 20
        }

        override fun setValue(thisRef: Any, property: KProperty<*>, value: Int) {
            println("委托属性为： ${property.name} 委托值为： $value")
        }

    }

    val d1: String by Delegate1()
    var d2: Int by Delegate2()
    // 测试
    @Test
    fun Test (){
        // 属性委托
       println(d1)
        println(d2)
    }

    class Test2{

    }


}