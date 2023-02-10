package com.chat.demo.mvvm.model

data class AiReq(
    val prompt:String,
    val model:String = "text-davinci-003",
    val temperature:Int=0,
    val max_tokens:Int=500,
    val top_p:Int = 1,
    val frequency_penalty:Double=0.0,
    val presence_penalty:Double=0.0
)
