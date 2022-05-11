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
        mediator.contract()
    }

}

class Tenant(mediator: Mediator) : Person {
    override fun contract(message: String) {
        TODO("Not yet implemented")
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

        }
    }

}