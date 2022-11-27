package com.juanmi.gamertool.repository.auth

import com.google.firebase.auth.FirebaseUser
import com.juanmi.gamertool.application.AuthResource

/***
 * Interfaz que define los métodos utilizados para la autenticación de usuarios.
 */
interface AuthRepository {
    val currentUser: FirebaseUser?
    suspend fun login(email: String, password: String): AuthResource<FirebaseUser>
    suspend fun signup(name: String, email: String, password: String): AuthResource<FirebaseUser>
    fun logout()
}