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