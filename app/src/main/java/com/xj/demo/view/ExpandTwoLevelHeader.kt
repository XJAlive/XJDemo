package com.xj.demo.view

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.scwang.smartrefresh.layout.api.RefreshHeader
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.constant.RefreshState
import com.scwang.smartrefresh.layout.constant.SpinnerStyle
import com.scwang.smartrefresh.layout.internal.InternalAbstract
import com.xj.demo.R

/**
 * Created by xiej 2023/2/13
 */
class ExpandTwoLevelHeader @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0,
) : InternalAbstract(context, attrs, defStyleAttr), RefreshHeader {

    companion object {
        var REFRESH_HEADER_FIRST_TIPS = "下拉有惊喜"

        var REFRESH_HEADER_PULL_TO_REFRESH = "下拉刷新"

        var REFRESH_HEADER_SECONDARY = "继续下拉有惊喜"

        var REFRESH_HEADER_RELEASE_SECONDARY = "松开有惊喜"
    }

    private var contentView: View
    private var tvTitle: TextView
    var mOffset = 0

    init {
        contentView =
            LayoutInflater.from(context).inflate(R.layout.layout_two_level_header, this, true)
        tvTitle = contentView.findViewById(R.id.tvTitle)
        tvTitle.text = REFRESH_HEADER_PULL_TO_REFRESH
        setBackgroundColor(Color.parseColor("#FE89C7"))
    }


    override fun onStateChanged(
        refreshLayout: RefreshLayout,
        oldState: RefreshState,
        newState: RefreshState,
    ) {
        super.onStateChanged(refreshLayout, oldState, newState)
        Log.i("xj", "onStateChanged，oldState = $oldState , newState = $newState")
        when(newState){
            RefreshState.ReleaseToRefresh ->{
                tvTitle.visibility = View.GONE
            }

            else ->{

            }
        }

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var height = View.MeasureSpec.getSize(heightMeasureSpec)
        val mode = View.MeasureSpec.getMode(heightMeasureSpec)
        height += mOffset
        Log.i("xj", "onMeasure.height=$height, offset=$mOffset")
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(height, mode))
    }

    override fun onMoving(
        isDragging: Boolean,
        percent: Float,
        offset: Int,
        height: Int,
        maxDragHeight: Int,
    ) {
        super.onMoving(isDragging, percent, offset, height, maxDragHeight)
        Log.i(
            "xj",
            "onMoving.isDragging=$isDragging, percent=$percent, offset=$offset, height=$height, maxDragHeight=$maxDragHeight"
        )

        mOffset = offset

        when {
            percent > 4 -> tvTitle.text = REFRESH_HEADER_RELEASE_SECONDARY
            percent > 3 -> tvTitle.text = REFRESH_HEADER_SECONDARY
            else -> {
                //NOTHING
            }
        }
        //修改高度
//        contentView.updateLayoutParams {
//            this.height += offset
//        }
    }

    override fun getSpinnerStyle(): SpinnerStyle {
        return SpinnerStyle.Translate
    }

    /**
     * 初次进来展示引导文案
     */
    fun setFirstRefreshTips(content: String) {
        tvTitle.text = content
    }

}