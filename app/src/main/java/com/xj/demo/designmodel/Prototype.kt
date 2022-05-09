package com.xj.demo.designmodel

import android.content.Intent
import android.net.Uri
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet

/**
 * 原型模式,当前Bean参数较多时考虑复制整个原型后修改
 */
class Prototype {

    fun main() {
        val intent = Intent(Intent.ACTION_SENDTO, Uri.EMPTY)
        intent.clone()

        val clContent: ConstraintLayout? = null
        val constraintSet = ConstraintSet().clone(clContent)
    }

}