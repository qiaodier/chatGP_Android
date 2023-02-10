package com.chat.demo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.chat.demo.R
import com.chat.demo.mvvm.model.ChatModel

class ChatAdapter:RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val CHAT_GPT = 1
    private val CHAT_SELF = 2

    private var mChatData:MutableList<ChatModel> = arrayListOf()

    fun setData(sources: List<ChatModel>) {
        mChatData = sources.toMutableList()
        notifyDataSetChanged()
    }

    fun addData(data: List<ChatModel>) {
        val start = mChatData.count()
        mChatData.addAll(data)
        notifyItemRangeInserted(start,data.size)
    }

    override fun getItemViewType(position: Int) = mChatData[position].userFrom

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType==CHAT_GPT) {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.listitem_chat_layout, parent, false)
            return GptViewHolder(view)
        }else{
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.listitem_self_layout, parent, false)
            return SelfViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = mChatData[position]
        if (holder is GptViewHolder){
            holder.gptContent.text = data.content
        }else if (holder is SelfViewHolder){
            holder.selfContent.text = data.content
        }
    }

    override fun getItemCount() = mChatData.size

    class GptViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val gptContent = itemView.findViewById<TextView>(R.id.tv_gpt)
    }

    class SelfViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val selfContent = itemView.findViewById<TextView>(R.id.tv_self)
    }
}