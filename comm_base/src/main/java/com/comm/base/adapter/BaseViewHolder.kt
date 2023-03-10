package com.comm.base.adapter

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

open class BaseViewHolder<T : ViewDataBinding>(val dataBinding: T) :
    RecyclerView.ViewHolder(dataBinding.root)