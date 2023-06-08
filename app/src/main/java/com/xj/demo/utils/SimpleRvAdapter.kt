package com.xj.demo.utils

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.xj.demo.R

/**
 * 简单装饰器
 * Created by xiej 2023/6/8 0008
 */
class SimpleRvAdapter(var context: Context, var datas: List<String>) :
    RecyclerView.Adapter<SimpleRvHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleRvHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.item_recyclerview, parent, false)
        return SimpleRvHolder(view)
    }

    override fun getItemCount() = datas.size

    override fun onBindViewHolder(holder: SimpleRvHolder, position: Int) {
        holder.name.text = datas[position]
        if (position == 0) {
            holder.itemView.setBackgroundColor(Color.parseColor("#3FB1FF"))
        } else {
            holder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF"))
        }
    }
}

class SimpleRvHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var name: TextView

    init {
        name = itemView.findViewById(R.id.tvContent)
    }
}