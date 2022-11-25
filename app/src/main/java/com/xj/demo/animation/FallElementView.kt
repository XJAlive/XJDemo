package com.xj.demo.animation

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.view.animation.LinearInterpolator
import androidx.core.animation.doOnEnd
import kotlin.random.Random

/**
 * 点赞散开和飘落组件
 */
@SuppressLint("ViewConstructor")
class FallElementView @JvmOverloads constructor(
    context: Context,
    private var element: Element<Bitmap>,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : View(context, attrs, defStyleAttr) {

    /**
     * 散开动画耗时
     */
    private val explodeDuration = 500L

    /**
     * 落下动画耗时
     */
    private val fallDuration = 800L

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        canvas?.drawBitmap(element.getResource(), 0F, 0F, paint)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val mWidthSpec = MeasureSpec.makeMeasureSpec(element.getResource().width,
            MeasureSpec.getMode(widthMeasureSpec))
        val mHeightSpec = MeasureSpec.makeMeasureSpec(element.getResource().height,
            MeasureSpec.getMode(widthMeasureSpec))
        super.onMeasure(mWidthSpec, mHeightSpec)
    }

    /**
     * 开始执行动画
     */
    fun startAnimation() {
        explode()
    }

    /**
     * 爆炸动画
     */
    private fun explode() {
        val translateXAnim =
            ObjectAnimator.ofFloat(this, "translationX", 0F, element.getExplodeOffset().x)
        translateXAnim.interpolator = LinearInterpolator()
        val translateYAnim =
            ObjectAnimator.ofFloat(this, "translationY", 0F, element.getExplodeOffset().y)
        translateXAnim.interpolator = DecelerateInterpolator()
        val scaleXAnim = ObjectAnimator.ofFloat(this,
            "scaleX",
            *element.getScaleRatioArray())
        translateXAnim.interpolator = LinearInterpolator()
        val scaleYAnim = ObjectAnimator.ofFloat(this, "scaleY", *element.getScaleRatioArray())
        translateXAnim.interpolator = LinearInterpolator()
        with(AnimatorSet()) {
            doOnEnd {
                //使用贝塞尔曲线，执行阶段2动画
                fallToEnd()
            }
            playTogether(translateXAnim, translateYAnim, scaleXAnim, scaleYAnim)
            duration = explodeDuration
            start()
        }
    }

    /**
     * 落下动画
     */
    private fun fallToEnd() {
        val typeEvaluator = BezierTypeEvaluator(element.getBezierAnchorPoint())
        val valueAnimator =
            ValueAnimator.ofObject(typeEvaluator, PointF(x, y), element.getEndPoint())
        valueAnimator.duration = fallDuration
        valueAnimator.startDelay = Random.nextLong(80)
        valueAnimator.interpolator = AccelerateDecelerateInterpolator()
        valueAnimator.addUpdateListener {
            val currentPoint = it.animatedValue as PointF
            translationX = currentPoint.x - element.getStartPoint().x
            translationY = currentPoint.y - element.getStartPoint().y
        }
        valueAnimator.start()
    }

    fun ensureLocation(x: Int, y: Int) =
        ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT).apply {
            setMargins(x, y, 0, 0)
        }

}

