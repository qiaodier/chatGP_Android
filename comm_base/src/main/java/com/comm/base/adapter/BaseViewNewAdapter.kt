package com.comm.base.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import java.lang.reflect.ParameterizedType

/**
 * V : ItemLayout 对应的 DataBinding
 * T : 实现类用到的实体类
 * VH: ItemLayout 对应的 ViewHolder
 */
abstract class BaseViewNewAdapter<V :ViewDataBinding,B> : RecyclerView.Adapter<BaseViewHolder<V>>() {
    /**
     * 布局id
     */
    abstract fun layoutId(): Int

    /**
     * 绑定数据
     */
    abstract fun bindViewHolder(holder: V, position: Int, data: B)

    //数据集合
    var dataList: MutableList<B> = mutableListOf()

    /**
     * 内部已经调用了notify
     */
    fun setData(sources: MutableList<B>) {
        dataList = sources
        notifyDataSetChanged()
    }

    fun addData(data: List<B>) {
        val start = dataList.count()
        dataList.addAll(data)
        notifyItemRangeInserted(start,data.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<V> {
        val binding = DataBindingUtil.inflate<V>(
            LayoutInflater.from(parent.context),
            layoutId(),
            parent,
            false
        )
        return BaseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<V>, position: Int) {
        bindViewHolder(holder.dataBinding, position, dataList[position])
    }

    override fun getItemCount(): Int {
        return dataList.size
    }


}