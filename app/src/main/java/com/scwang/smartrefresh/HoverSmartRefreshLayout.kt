package com.scwang.smartrefresh

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.constant.RefreshState
import kotlin.math.max
import kotlin.math.min
import kotlin.math.pow

/**
 * 悬停SmartRefreshLayout
 *   1、在原SmartRefreshLayout基础上增加悬停功能：
 *      1/1首次加载支持显示RefreshHeader内容在屏幕中
 *
 *
 * Created by xiej 2023/2/15
 */
class HoverSmartRefreshLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null,
) : SmartRefreshLayout(context, attrs) {

    var unConsumeDy = 0F

    override fun moveSpinnerInfinitely(spinner: Float) {
        if (unConsumeDy > 0 && spinner <= unConsumeDy) {
            //开始向上滑动，模拟消费掉unConsumeDy
            unConsumeDy = 0F.coerceAtLeast(spinner - 10)
        }
        val newSpinner = spinner + unConsumeDy
        super.moveSpinnerInfinitely(newSpinner)
        Log.e("xj", "spinner = $spinner, newSpinner = $newSpinner")
    }

    fun moveSpinner(spinner: Float) {
        val M = (mHeaderMaxDragRate * mHeaderHeight).toDouble()
        val H = max(mScreenHeightPixels / 2, height).toDouble()
        val x = max(0f, spinner * mDragRate).toDouble()
        val y = min(
            M * (1 - 100.0.pow(-x / if (H == 0.0) 1.0 else H)), x
        ) // 公式 y = M(1-100^(-x/H))
        mKernel.moveSpinner(y.toInt(), false)

        unConsumeDy = y.toFloat()
    }

    override fun notifyStateChanged(state: RefreshState?) {
        super.notifyStateChanged(state)
        if (RefreshState.RefreshFinish == state) {
            unConsumeDy = 0F
        }
    }


}