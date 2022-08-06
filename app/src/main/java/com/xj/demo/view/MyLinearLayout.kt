package com.xj.demo.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.widget.LinearLayout

class MyLinearLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
        Log.i("xj", "MyLinearLayout dispatchTouchEvent $event")
        return super.dispatchTouchEvent(event)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return super.onInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        Log.i("xj", "MyLinearLayout onTouchEvent $event")

        return super.onTouchEvent(event)
    }

}