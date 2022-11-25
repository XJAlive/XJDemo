package com.xj.demo.animation

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.xj.demo.R

/**
 * Created by xiej on 2021/3/1
 */
class AnimationActivity : AppCompatActivity() {

    private lateinit var fallView: FallView
    private lateinit var vStart: View
    private lateinit var vEnd: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animation)
        fallView = findViewById(R.id.fallView)
        vStart = findViewById(R.id.vStart)
        vEnd = findViewById(R.id.vEnd)
    }

    fun startAnimation(view: View) {
        fallView.showAnimation(vStart.x, vStart.y, vEnd.x, vEnd.y)
    }

}


