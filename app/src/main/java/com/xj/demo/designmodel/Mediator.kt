package com.xj.demo.designmodel

/**
 * 中介模式
 */
interface Person {

    fun contract(message: String)
}

/**
 *
 */
class Owner(var mediator: Mediator) : Person {
    override fun contract(message: String) {
        mediator.contract(message, this)
    }

    fun receiveMessage(message: String) {
        println("房主收到消息:$message")
    }
}

class Tenant(var mediator: Mediator) : Person {
    override fun contract(message: String) {
        mediator.contract(message, this)
    }

    fun receiveMessage(message: String) {
        println("租客收到消息:$message")
    }
}


abstract class Mediator {

    abstract fun contract(message: String, person: Person)

}

class MediatorStructure : Mediator() {

    var owner: Owner? = null
    var tenant: Tenant? = null

    override
    fun contract(message: String, person: Person) {
        if (person is Owner) {
            tenant?.receiveMessage(message)
        } else if (person is Tenant) {
            owner?.receiveMessage(message)
        }
    }

}