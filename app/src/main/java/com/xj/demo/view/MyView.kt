package com.xj.demo.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View

class MyView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {


    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
        Log.w("xj", "MyView dispatchTouchEvent $event")
        return super.dispatchTouchEvent(event)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        Log.w("xj", "MyView onTouchEvent $event")

        return super.onTouchEvent(event)
    }

}