package com.xj.demo.designmodel

open class Water {

}

class Coffee1 : Water() {

}

class Tea1 : Water() {

}

class Coffee2 : Water() {

}

class Tea2 : Water() {

}

open class Shop {

}

class Starbucks1 : Shop() {
    fun sellCoffee(): Water {
        return Coffee1()
    }
}

class Starbucks2 : Shop() {
    fun sellCoffee(): Water {
        return Coffee2()
    }
}


class Luckin1 : AbstractShop() {
    override fun sellCoffee(): Water {
        return Coffee1()
    }

    override fun sellTea(): Water {
        return Coffee1()
    }
}


class Luckin2 : AbstractShop() {
    override fun sellCoffee(): Water {
        return Coffee2()
    }

    override fun sellTea(): Water {
        return Tea2()
    }
}

abstract class AbstractShop : Shop() {
    abstract fun sellCoffee(): Water
    abstract fun sellTea(): Water
}