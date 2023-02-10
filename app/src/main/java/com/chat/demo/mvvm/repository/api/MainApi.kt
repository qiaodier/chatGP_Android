package com.chat.demo.mvvm.repository.api

import com.chat.demo.mvvm.model.AiReq
import com.chat.demo.mvvm.model.AiResp
import com.comm.http.model.BaseRespModel
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface MainApi {
    @Headers(
        "Content-Type:application/json",
        "Authorization:Bearer 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")
    @POST("v1/completions")
    suspend fun getAiContent(@Body req: AiReq): AiResp
}