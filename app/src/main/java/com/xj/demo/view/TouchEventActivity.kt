package com.xj.demo.view

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import com.xj.demo.R

/**
 * Created by xiej on 2021/3/1
 */
class TouchEventActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_touch)
    }

    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
        Log.d("xj", "Activity dispatchTouchEvent $event")

        return super.dispatchTouchEvent(event)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        Log.d("xj", "Activity onTouchEvent $event")

        return super.onTouchEvent(event)
    }

}


