package com.comm.http.exception

/**
 * @author iqiao
 * @date 2018/2/27
 * 描述：服务器下发的错误
 */
class ServerException(var code: Int, message: String?) :
    RuntimeException(message)