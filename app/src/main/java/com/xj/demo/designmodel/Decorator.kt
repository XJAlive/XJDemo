package com.xj.demo.designmodel

/**
 * 装饰器模式，在不改动原有类的基础上扩展类的功能
 */
abstract class Aldult {
    abstract fun dressUp()
}

class Man : Aldult() {
    override fun dressUp() {
        println("wear T-shirt")
    }
}

class Decorator(var person: Aldult) : Aldult() {
    override fun dressUp() {
        wearHat()
        person.dressUp()
        wearShoes()
    }

    fun wearHat() {
        println("wear hat")
    }

    fun wearShoes() {
        println("wear shoes")
    }

}