package com.xj.demo.animation

import android.graphics.Bitmap
import android.graphics.PointF

class BitmapElement(
    var bitmap: Bitmap,
    var start: PointF,
    var end: PointF,
    var offset: PointF,
    var anchor: PointF,
) : Element<Bitmap> {
    override fun getResource() = bitmap

    override fun getStartPoint() = start

    override fun getEndPoint() = end

    override fun getExplodeOffset(): PointF {
        return offset
    }

    override fun getBezierAnchorPoint(): PointF {
        return anchor
    }

    override fun getScaleRatioArray() =
        floatArrayOf(0.2F, 0.3F, 0.4F, 0.5F, 0.6F, 0.7F, 0.8F, 0.9F, 1F)
}

interface Element<T> {

    fun getResource(): T

    fun getStartPoint(): PointF

    fun getEndPoint(): PointF

    /**
     * 缩放比例
     */
    fun getScaleRatioArray(): FloatArray

    /**
     * 散开后距离起始点位置
     */
    fun getExplodeOffset(): PointF

    /**
     * 二阶曲线锚点
     */
    fun getBezierAnchorPoint(): PointF


}