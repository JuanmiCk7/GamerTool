package com.juanmi.gamertool.application

/***
 * Clase utilizada para la encapsulación del resultado de la autenticación en Firebase
 */
sealed class AuthResource<out R> {
    data class Success<out R>(val result: R) : AuthResource<R>()
    data class Failure(val exception: Exception) : AuthResource<Nothing>()
    object Loading : AuthResource<Nothing>()
}
