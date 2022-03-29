package com.xj.demo.contentprovider

import android.content.ContentValues
import android.database.ContentObserver
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.xj.demo.R

class ProviderActivity : AppCompatActivity() {

    private val uri = Uri.parse("content://com.xj.provider/user")

    val handle = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)

            Log.i("xj", "收到handler回调")
        }
    }

    private lateinit var observer: ContentObserver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_provider)

        observer = object : ContentObserver(handle) {
            override fun onChange(selfChange: Boolean) {
                super.onChange(selfChange)
                Log.i("xj", "onChange回调")
            }
        }

        contentResolver.registerContentObserver(uri, true, observer)
    }

    override fun onDestroy() {
        super.onDestroy()
        contentResolver?.unregisterContentObserver(observer)
    }

    fun insert(view: View) {
        val contentValue = ContentValues()
        contentValue.put("id", System.currentTimeMillis() / 1000)
        contentValue.put("user_name", "ibiza")

        contentResolver.insert(uri, contentValue)
    }

    fun query(view: View) {
//        val newUri = ContentUris.withAppendedId(uri,1)
        val cursor = contentResolver.query(uri, arrayOf("id,user_name"), null, null, null) ?: return
        while (cursor.moveToNext()) {
            Log.i("xj", "id=${cursor.getInt(0)} user_name=${cursor.getString(1)}")
        }
        cursor.close()
    }
}