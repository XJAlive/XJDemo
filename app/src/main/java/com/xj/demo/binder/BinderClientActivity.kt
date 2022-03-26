package com.xj.demo.binder

import android.os.Binder
import android.os.Bundle
import android.os.IBinder
import android.os.IInterface
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.xj.demo.R

/**
 * Created by xiej on 2021/3/1
 */
class BinderClientActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_binder)
        //AMS跨进程
        Toast.makeText(this, intent.getStringExtra("name"), Toast.LENGTH_SHORT).show()
    }

    fun start(view: View) {
        Stub().attachInterface(object : IInterface {
            override fun asBinder(): IBinder {
                return Binder()
            }
        }, "com.xj.provider")
    }


}


