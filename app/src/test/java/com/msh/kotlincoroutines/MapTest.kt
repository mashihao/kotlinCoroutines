package com.msh.kotlincoroutines

import org.junit.Test
import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * @author : 马世豪
 * time : 2021/5/31 16
 * email : ma_shihao@yeah.net
 * des :
 */
class MapTest {


    data class Game(var level: String, var gameName: String, var idear: Idear)
    data class Idear(var ideName: String, var version: String)

    data class User(var userName: String, var sex: Boolean, var game: Game)

    @Test
    fun test() {
        var idear = Idear("Android studio", "1.2")
        var game = Game("1", "GameName", idear)
        var user = User("woshi value", false, game)

        var string = "game"
        println("gamg".split("."))
        println(getValue(string, user))

    }

    private val linePattern: Pattern = Pattern.compile("_(\\w)")

    /** 下划线转驼峰  */
    fun lineToHump(str: String): String? {
        var str = str
        str = str.toLowerCase()
        val matcher: Matcher = linePattern.matcher(str)
        val sb = StringBuffer()
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase())
        }
        matcher.appendTail(sb)
        return sb.toString()
    }
    fun getValue(arg: String, user: User): Any {

        val split = arg.split(".").toMutableList()
        var returnValue:Any = "null"
        var startValue: Any = user

        split.forEach {it->
            val clazz = startValue.javaClass
            val field = clazz.getDeclaredField(it)
            field.isAccessible = true
            returnValue = field.get(startValue)
            startValue = returnValue
        }
        return returnValue
    }
}