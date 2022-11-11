package com.juanmi.gamertool.core.network

import java.io.IOException
import java.util.concurrent.TimeoutException

sealed class ResultData<out T> {
    data class Success<out T>(val data: T, val code: Int = DEFAULT_SUCCESS_CODE) : ResultData<T>()
    data class Error(val failure: Exception, val code: Int = DEFAULT_ERROR_CODE) :
        ResultData<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[code=$code, data=$data]"
            is Error -> "Error[code=$code, failure=$failure]"
        }
    }

    fun isNetworkError(): Boolean {
        return this is Error && (failure is IOException || failure is TimeoutException)
    }

    fun getErrorCode(): Int {
        return if (this is Error) code else NOT_ERROR_CODE
    }

    fun getResponseCode(): Int {
        return if (this is Success<*>) code else NOT_SUCCESS_CODE
    }

    companion object {
        const val DEFAULT_ERROR_CODE = -999
        const val NOT_ERROR_CODE = -900
        const val DEFAULT_SUCCESS_CODE = -888
        const val NOT_SUCCESS_CODE = -800
    }
}