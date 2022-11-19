package com.juanmi.gamertool.repository.auth

import com.google.firebase.auth.FirebaseUser
import com.juanmi.gamertool.core.AuthResource


interface AuthRepository {
    val currentUser: FirebaseUser?
    suspend fun login(email: String, password: String): AuthResource<FirebaseUser>
    suspend fun signup(name: String, email: String, password: String): AuthResource<FirebaseUser>
    fun logout()
}