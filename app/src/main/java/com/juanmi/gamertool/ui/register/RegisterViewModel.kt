package com.juanmi.gamertool.ui.register

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseUser
import com.juanmi.gamertool.application.AuthResource
import com.juanmi.gamertool.repository.auth.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    private val _loginFlow = MutableStateFlow<AuthResource<FirebaseUser>?>(null)
    val loginFlow: StateFlow<AuthResource<FirebaseUser>?> = _loginFlow

    private val _signupFlow = MutableStateFlow<AuthResource<FirebaseUser>?>(null)
    val signupFlow: StateFlow<AuthResource<FirebaseUser>?> = _signupFlow

    init {
        if (repository.currentUser != null) {
            _loginFlow.value = AuthResource.Success(repository.currentUser!!)
        }
    }

    fun signupUser(name: String, email: String, password: String) = viewModelScope.launch {
        _signupFlow.value = AuthResource.Loading
        val result = repository.signup(name, email, password)
        _signupFlow.value = result
    }

    fun logged(view: View) {
        val action = RegisterFragmentDirections.actionRegisterFragmentToMainFragment()
        view.findNavController().navigate(action)
    }

    fun toLoginFragment(view: View) {
        val action = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
        view.findNavController().navigate(action)
    }
}