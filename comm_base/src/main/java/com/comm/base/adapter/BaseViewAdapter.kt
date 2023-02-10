package com.comm.base.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.comm.base.extensions.getViewDataBinding

/**
 * V : ItemLayout 对应的 DataBinding
 * T : 实现类用到的实体类
 * VH: ItemLayout 对应的 ViewHolder
 */
abstract class BaseViewAdapter<V :ViewDataBinding,T, VH : BaseViewHolder<V>> : RecyclerView.Adapter<VH>() {
    /**
     * 布局id
     *
     */
    abstract fun layoutId(): Int

    /**
     * 绑定数据
     */
    abstract fun bindViewHolder(holder: VH, position: Int, data: T)

    /**
     * 创建viewHolder
     */
    abstract fun returnViewHolder(viewDataBinding: V):VH

    //数据集合
    var dataList: MutableList<T> = mutableListOf()

    /**
     * 内部已经调用了notify
     */
    fun setData(sources: List<T>) {
        dataList = sources.toMutableList()
        notifyDataSetChanged()
    }

    fun addData(count: Int, data: List<T>) {
        val start = dataList.count()
        dataList.addAll(data)
        notifyItemRangeInserted(start,data.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = DataBindingUtil.inflate<V>(
            LayoutInflater.from(parent.context),
            layoutId(),
            parent,
            false
        )
        return returnViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        bindViewHolder(holder, position, dataList[position])
    }

    override fun getItemCount(): Int {
        return dataList?.size
    }


}