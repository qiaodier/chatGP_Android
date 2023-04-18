package com.chat.demo.mvvm.repository

import com.chat.demo.mvvm.model.AiReq
import com.chat.demo.mvvm.repository.api.MainApi
import com.comm.http.RequestCreator
import com.comm.http.repository.BaseRepository

class MainRepository:BaseRepository() {

    private val mRequestApi by lazy { mRequestClient.get(MainApi::class.java) }

    suspend fun getAiContent(req: AiReq) = callRequest {
        handleResponse(
            mRequestApi.getAiContent(req)
        )
    }
}