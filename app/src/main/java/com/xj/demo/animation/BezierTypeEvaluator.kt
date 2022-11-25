package com.xj.demo.animation

import android.animation.TypeEvaluator
import android.graphics.PointF

/**
 * 二阶贝塞尔计算运动轨迹
 */
class BezierTypeEvaluator(var anchorPoint: PointF) : TypeEvaluator<PointF> {

    override fun evaluate(fraction: Float, startValue: PointF, endValue: PointF): PointF {
        return calculateBezierPointForQuadratic(fraction, startValue, anchorPoint, endValue)
    }

    private fun calculateBezierPointForQuadratic(
        t: Float,
        start: PointF,
        anchor: PointF,
        end: PointF,
    ): PointF {
        val point = PointF()
        val temp = 1 - t
        point.x = temp * temp * start.x + 2 * t * temp * anchor.x + t * t * end.x
        point.y = temp * temp * start.y + 2 * t * temp * anchor.y + t * t * end.y
        return point
    }

}