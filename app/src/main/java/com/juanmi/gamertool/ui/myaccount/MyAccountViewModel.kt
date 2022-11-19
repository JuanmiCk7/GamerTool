package com.juanmi.gamertool.ui.myaccount

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseUser
import com.juanmi.gamertool.repository.auth.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyAccountViewModel @Inject constructor(
    private val authRepository: AuthRepository,
): ViewModel() {

    fun logout(view: View) {
        authRepository.logout()
        val action = MyAccountFragmentDirections.actionMyAccountFragmentToLoginFragment()
        view.findNavController().navigate(action)
    }

    fun getCurrentUser() : FirebaseUser? {
        return authRepository.currentUser
    }
}