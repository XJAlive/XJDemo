package com.xj.demo.activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.scwang.smartrefresh.HoverSmartRefreshLayout
import com.xj.demo.R
import com.xj.demo.utils.SimpleRvAdapter
import com.xj.demo.view.ExpandTwoLevelHeader


/**
 * Created by xiej 2023/2/14
 */
class TwoLevelActivity : AppCompatActivity() {

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_two_level)

        val srf = findViewById<HoverSmartRefreshLayout>(R.id.srf)
        val rv = findViewById<RecyclerView>(R.id.rv)

        val header = ExpandTwoLevelHeader(this)
        srf.setRefreshHeader(header)
        val list = List(20) { it.toString() }
        val adapter = SimpleRvAdapter(this, list)
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter
        adapter.notifyDataSetChanged()
        srf.setHeaderMaxDragRate(20F)
        srf.post {
            srf.moveSpinner(600F)
        }
    }
}