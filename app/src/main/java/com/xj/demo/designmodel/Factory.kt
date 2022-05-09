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

    fun getSystemService(name: String): Any? {
        if (Context.WINDOW_SERVICE == name) {
            return mWindowManager
        } else if (Context.SEARCH_SERVICE == name) {
            return mSearchManager
        }
        return null
    }
}

