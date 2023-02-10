package com.chat.demo.mvvm.model

data class RealContent(
    val text:String,
    val index:Int,
    val logprobs:String,
    val finish_reason:String
)
