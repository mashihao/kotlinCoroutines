package com.msh.kotlincoroutines

import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.runBlocking
import org.junit.Test

/**
 * @author : 马世豪
 * time : 2021/5/26 16
 * email : ma_shihao@yeah.net
 * des :
 */
class Group {


    data class User(val id: Int, val name: String){

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is User) return false

            if (id != other.id) return false
            if (name != other.name) return false

            return true
        }

        override fun hashCode(): Int {
            var result = id
            result = 31 * result + name.hashCode()
            return result
        }
    }

    @Test
    fun test() {
        var i = 1.2
        println(Math.round(i).toInt())
        i=1.49
        println(Math.round(i).toInt())
        i=1.89
        println(Math.round(i).toInt())
        i=1.99
        println(Math.round(i).toInt())
        i=1.51
        println(Math.round(i).toInt())

        var listA :MutableList<User> = mutableListOf()
        var listB:MutableList<User> = mutableListOf<User>()
        for (i in 0 until 10) {
            listA.add(User(i, "${i}"))
        }

        for (i in 7 until 15) {
            listB.add(User(i, "${i}"))
        }

        runBlocking {
            flowOf(listA).zip(flowOf(listB)){ a, b ->

                var newlist = (a + b).groupBy {
                    it.id
                }.filter {
                    println("${it.value.size==1}")
                    it.value.size==1
                }.flatMap {
                    it.value
                }.filter {
                    println("in----${it !in a}")
                    it !in a
                }

                print(newlist)
                newlist
            }.collect {

            }
        }
        
        


    }
}