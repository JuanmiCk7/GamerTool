package com.juanmi.gamertool.repository.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.juanmi.gamertool.core.AuthResource
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : AuthRepository {

    override val currentUser: FirebaseUser?
        get() = firebaseAuth.currentUser

    override suspend fun login(email: String, password: String): AuthResource<FirebaseUser> {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            AuthResource.Success(result.user!!)
        } catch (e: Exception) {
            e.printStackTrace()
            AuthResource.Failure(e)
        }
    }

    override suspend fun signup(name: String, email: String, password: String): AuthResource<FirebaseUser> {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            result.user?.updateProfile(UserProfileChangeRequest.Builder().setDisplayName(name).build())?.await()
            return AuthResource.Success(result.user!!)
        } catch (e: Exception) {
            e.printStackTrace()
            AuthResource.Failure(e)
        }
    }

    override fun logout() {
        firebaseAuth.signOut()
    }

}