package com.xj.demo.binder

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.xj.demo.R

/**
 * Created by xiej on 2021/3/1
 */
class BinderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_binder)
    }

    fun start(view: View) {

        startActivity(Intent(this, BinderClientActivity::class.java).apply {
            putExtra(
                "name",
                "kobe"
            )
        })
    }


}


