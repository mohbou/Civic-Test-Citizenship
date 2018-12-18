package com.mohbou.enhancedtestcivic.domain

sealed class ErrorType(val error:String?=null) {

    class NetworkError:ErrorType()
    class UnknownError(error:String?):ErrorType(error)

}
