package com.juanmi.gamertool.ui.login

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
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    private val _loginFlow = MutableStateFlow<AuthResource<FirebaseUser>?>(null)
    val loginFlow: StateFlow<AuthResource<FirebaseUser>?> = _loginFlow

    init {
        if (repository.currentUser != null) {
            _loginFlow.value = AuthResource.Success(repository.currentUser!!)
        }
    }

    fun loginUser(email: String, password: String) = viewModelScope.launch {
        _loginFlow.value = AuthResource.Loading
        val result = repository.login(email, password)
        _loginFlow.value = result
    }

    fun logged(view: View) {
        val action = LoginFragmentDirections.actionLoginFragmentToMainFragment()
        view.findNavController().navigate(action)
    }

    fun toRegisterFragment(view: View) {
        val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
        view.findNavController().navigate(action)
    }
}


