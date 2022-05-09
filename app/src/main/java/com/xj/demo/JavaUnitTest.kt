package com.xj.demo

import androidx.annotation.IntegerRes
import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.HashSet


fun main() {

    val set = HashSet<Int>()
    val map = HashMap<Any?, Any>()
    val table = Hashtable<Any?,Any>()
    map["userName"] = "ming"
    map[null] = "1213"
    println(map)
    println(map.get(null))
    println((0 as Int).hashCode())


}