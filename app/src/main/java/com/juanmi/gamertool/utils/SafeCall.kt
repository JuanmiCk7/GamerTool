package com.juanmi.gamertool.utils

import com.juanmi.gamertool.application.api.ResultData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response

/***
 * Clase utilizada para controlar los posibles errores producidos al hacer la petición a la API.
 */
internal suspend fun <T> safeCall(dispatcher: CoroutineDispatcher, call: suspend () -> Response<T>): ResultData<T?> {
    return withContext(dispatcher) {
        try {
            val response = call.invoke()
            if (response.isSuccessful) {
                response.body()?.let { ResultData.Success(response.body(), response.code()) }
                    ?: run { ResultData.Error(Exception(response.message()), response.code()) }
            } else {
                ResultData.Error(HttpException(response), response.code())
            }
        } catch (e: Exception) {
            ResultData.Error(e)
        }
    }
}