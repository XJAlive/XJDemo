package com.xj.demo.designmodel

import android.app.AlertDialog
import android.content.Context

/**
 * 构造者模式
 */
fun showDialog(context: Context) {
    AlertDialog.Builder(context)
        .setTitle("弹窗标题")
        .setCancelable(true)
        .create()
        .show()
}