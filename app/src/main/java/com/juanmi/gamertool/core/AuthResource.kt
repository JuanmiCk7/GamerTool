package com.juanmi.gamertool.core



sealed class AuthResource<out R> {
    data class Success<out R>(val result: R) : AuthResource<R>()
    data class Failure(val exception: Exception) : AuthResource<Nothing>()
    object Loading : AuthResource<Nothing>()
}
