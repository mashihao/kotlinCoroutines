package com.msh.kotlincoroutines

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Test

/**
 * @author : 马世豪
 * time : 3/31/21 13
 * email : ma_shihao@yeah.net
 * des :
 */
class FlowTest {
    /*P1,P2 表示参数值，R表示返回值
       addThen为扩展方法
       infix中缀表达式
       Function<P1,P2>  p1为参数类型，P2为返回值类型
   */
    infix  fun <P1,P2,R > Function1<P1,P2>.andThen(function: Function1<P2,R>):Function1<P1,R>{
        return fun (p1:P1):R{
            //返回了一个函数，函数里面又把function这个参数调用了一遍
            //调用的时候又把自己调用了一遍，把自己的返回值传给function
            return function.invoke(this.invoke(p1))
        }
    }

    @Test
    fun flowtest() {


        val add5={i:Int -> i+5}     //f(g(x))
        val mulyiplyBy2={i:Int ->i*2} // m(x)=f(g(x))

        fun main(args: Array<String>) {

            println(mulyiplyBy2(add5(8)))       //(5+8)*2

            val k= add5 andThen mulyiplyBy2
            println(k(8))
        }



        runBlocking{
            flow {
                delay(1000)
                println("Top")
                emit(4)
            }.onStart {
              println("onStart")
            }.
            transform {
                delay(2000)
                println("center")
                emit(it * it)
            }.transform {
                delay(1000)
                println("end")
                emit("$it")
            }.onCompletion {
                println("onCompletion")
            }
                .collect {
                println("----${it}")
            }

            flow {
                emit(1)
                emit(2)
                emit(3)
                emit(4)
            }.dropWhile {
                it % 2 == 0
            }.collect {
                println(it)
            }


           var flow1 = flow {
                emit(1)
                delay(1000)
                emit(2)
                delay(1000)
                emit(3)
                delay(2000)
                emit(4)
            }

            println(flow1.toList())

        }
    }
}