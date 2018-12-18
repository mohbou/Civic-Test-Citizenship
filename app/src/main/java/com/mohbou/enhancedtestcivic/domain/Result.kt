package com.mohbou.enhancedtestcivic.domain

import java.io.IOException

class Result<T>(val data:T? =null,val error:Throwable?=null) {

    val success:Boolean = data !=null
    var errorType:ErrorType? = null

    companion object {
        fun <T> fromData(data:T):Result<T> = Result(data,null)
        fun <T> fromError(error:Throwable):Result<T> {
            val result = Result<T>(null,error)
            when(error) {
                is IOException -> result.errorType = ErrorType.NetworkError()
                else -> ErrorType.UnknownError(error.message)
            }
            return result
        }
    }

}