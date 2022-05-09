package com.xj.demo

import android.util.ArrayMap
import android.util.LruCache
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method

fun main() {
//    testInline()
//    val sparseArray = SparseArray<Any>()
//    sparseArray.put(1, 2)
//    val arrayMap = ArrayMap<Int, String>()
//    for (i in 1..10) {
//        arrayMap[i] = i.toString()
//    }
//    val arrayMap2 = ArrayMap<Int, String>()
//    val hashSet = HashSet<Int>()
//    val cache: LruCache<String, String>

    val linkedHashMap: LinkedHashMap<String, String> = LinkedHashMap(16, 0.75f, true)
    linkedHashMap.put("name1", "josan1")
    linkedHashMap.put("name2", "josan2")
    linkedHashMap.put("name3", "josan3")
    println("开始时顺序：")
    val iterator = linkedHashMap.entries.iterator()
    while (iterator.hasNext()) {
        val entry: Map.Entry<*, *> = iterator.next()
        val key = entry.key as String
        val value = entry.value as String
        println("key:$key,value:$value")
    }
    println("通过get方法，导致key为name1对应的Entry到表尾")
    linkedHashMap.get("name1")
    val set2 = linkedHashMap.entries
    val iterator2 = set2.iterator()
    while (iterator2.hasNext()) {
        val entry: Map.Entry<*, *> = iterator2.next()
        val key = entry.key as String
        val value = entry.value as String
        println("key:$key,value:$value")
    }
}


class ProxyClass : InvocationHandler {

    override fun invoke(proxy: Any?, method: Method?, args: Array<out Any>?): Any {
        return ""
    }
}

fun testInline() {
    testClosure {
        "error"
    }
}

private inline fun testClosure(test: (String) -> String) {
    println("step 1")
    println(test("step test"))
    println("step 2")
}