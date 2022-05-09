package com.xj.demo.designmodel

import android.app.SearchManager
import android.content.Context
import android.view.WindowManager

/**
 * 简单工厂
 */
class ContextWrapper1 {

    private var mWindowManager: WindowManager? = null
    private var mSearchManager: SearchManager? = null

    /**
     * 简单工厂
     */
    fun getSystemService(name: String): Any? {
        if (Context.WINDOW_SERVICE == name) {
            return mWindowManager
        } else if (Context.SEARCH_SERVICE == name) {
            return mSearchManager
        }
        return null
    }

    /**
     * 工厂模式
     */
    fun factory() {
        val starbucks = Starbucks1()
        //具体实现交由工厂完成
        val coffee = starbucks.sellCoffee()

        val luckin = Luckin1()
        val coffee1 = luckin.sellCoffee()
    }

    /**
     * 抽象工厂
     */
    fun abstractFactory() {
        //工厂进行抽象，使用时不关心具体实现
        val shop = Luckin1()
        shop.sellCoffee()
        shop.sellTea()

        val shop2 = Luckin2()
        shop2.sellCoffee()
        shop2.sellTea()
    }

}


