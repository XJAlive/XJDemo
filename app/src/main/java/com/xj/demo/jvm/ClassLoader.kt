package com.xj.demo.jvm

class MyClassLoader : ClassLoader() {

    override fun loadClass(name: String?): Class<*> {
//        if (name.isNullOrBlank()) throw ClassNotFoundException()
//        kotlin.runCatching {
//            val className = name.substring(name.lastIndexOf(".") + 1) + ".class"
//            val ips = javaClass.getResourceAsStream(className) ?: return super.loadClass(name)
//
//            val bytes = ByteArray(ips.available())
//            //将数组写进bytes
//            ips.read(bytes)
//            return defineClass(name, bytes, 0, bytes.size)
//        }.onFailure {
//            throw it
//        }

        return super.loadClass(name)
    }

    override fun findClass(name: String?): Class<*> {
        if (name.isNullOrBlank()) throw ClassNotFoundException()
        kotlin.runCatching {
            val className = name.substring(name.lastIndexOf(".") + 1) + ".class"
            val ips = javaClass.getResourceAsStream(className) ?: return super.loadClass(name)

            val bytes = ByteArray(ips.available())
            //将数组写进bytes
            ips.read(bytes)
            return defineClass(name, bytes, 0, bytes.size)
        }.onFailure {
            throw it
        }

        return super.findClass(name)
    }

}

fun main() {
    val name = "com.xj.demo.jvm.SuperClass"
    val obj = MyClassLoader().loadClass(name).newInstance()
    println(obj.javaClass)
    println(obj is SuperClass)
}