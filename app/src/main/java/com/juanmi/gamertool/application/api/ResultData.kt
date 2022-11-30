package com.juanmi.gamertool.application.api

import java.io.IOException
import java.util.concurrent.TimeoutException

/***
 * Clase utilizada para encapsular los resultados de la petición a la API IGDB.
 */
sealed class ResultData<out T> {
    data class Success<out T>(val data: T, val code: Int = DEFAULT_SUCCESS_CODE) : ResultData<T>()
    data class Error(val failure: Exception, val code: Int = DEFAULT_ERROR_CODE) :
        ResultData<Nothing>()

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
        const val DEFAULT_ERROR_CODE = -404
        const val NOT_ERROR_CODE = -500
        const val DEFAULT_SUCCESS_CODE = -200
        const val NOT_SUCCESS_CODE = -400
    }
}