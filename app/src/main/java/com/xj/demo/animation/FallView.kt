package com.xj.demo.animation

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.PointF
import android.util.AttributeSet
import android.widget.FrameLayout
import com.xj.demo.R

/**
 * 点赞动画
 * 阶段1：多个心形图标以抛物线方式,围绕起始位置爆炸式散开
 * 阶段2：以二阶贝塞尔曲线方式落入终点位置
 */
class FallView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null,
) : FrameLayout(context, attrs) {

    private val bitmap1 = BitmapFactory.decodeResource(resources, R.mipmap.ic_heart_pink)
    private val bitmap2 = BitmapFactory.decodeResource(resources, R.mipmap.ic_heart_orange)
    private val bitmap3 = BitmapFactory.decodeResource(resources, R.mipmap.ic_heart_yellow)
    private val bitmap4 = BitmapFactory.decodeResource(resources, R.mipmap.ic_heart_blue)

    fun showAnimation(startX: Float, startY: Float, endX: Float, endY: Float) {
        val bitmapElement1 =
            BitmapElement(bitmap1,
                PointF(startX, startY),
                PointF(endX, endY),
                PointF(90F, 90F),
                PointF(startX + 90, startY - 20))
        val child1 = FallElementView(context, bitmapElement1)
        addView(child1, child1.ensureLocation(startX.toInt(), startY.toInt()))

        val bitmapElement2 =
            BitmapElement(bitmap2,
                PointF(startX, startY),
                PointF(endX, endY),
                PointF(-100F, 90F),
                PointF(startX - 20, startY - 20))
        val child2 = FallElementView(context, bitmapElement2)
        addView(child2, child2.ensureLocation(startX.toInt(), startY.toInt()))

        val bitmapElement3 =
            BitmapElement(bitmap3,
                PointF(startX, startY),
                PointF(endX, endY),
                PointF(-70F, -100F),
                PointF(startX - 90, startY - 40))
        val child3 = FallElementView(context, bitmapElement3)
        addView(child3, child3.ensureLocation(startX.toInt(), startY.toInt()))

        val bitmapElement4 =
            BitmapElement(bitmap4,
                PointF(startX, startY),
                PointF(endX, endY),
                PointF(70F, -40F),
                PointF(startX + 40, startY - 20))
        val child4 = FallElementView(context, bitmapElement4)
        addView(child4, child4.ensureLocation(startX.toInt(), startY.toInt()))

        child1.startAnimation()
        child2.startAnimation()
        child3.startAnimation()
        child4.startAnimation()
    }

}