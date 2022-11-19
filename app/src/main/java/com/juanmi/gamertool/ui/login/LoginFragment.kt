package com.juanmi.gamertool.ui.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import com.google.firebase.auth.FirebaseUser
import com.google.rpc.context.AttributeContext
import com.juanmi.gamertool.core.AuthResource
import com.juanmi.gamertool.databinding.LoginFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: LoginFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = LoginFragmentBinding.inflate(inflater, container, false)
        binding.btnLogin.setOnClickListener {
            if(checkIfUserNotEmpty() && checkIfPasswordNotEmpty()) {
                viewModel.loginUser(binding.etEmail.text.toString(), binding.etPassword.text.toString())
            } else {
                Toast.makeText(requireContext(), "Not empty fields, please", Toast.LENGTH_SHORT).show()
            }
        }

        binding.rightBtn.setOnClickListener {
            viewModel.toRegisterFragment(requireView())
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loginFlow.asLiveData().observe(viewLifecycleOwner) {
            when(it) {
                is AuthResource.Failure -> {
                    Toast.makeText(requireContext(), "Failed login", Toast.LENGTH_SHORT).show()
                }
                is AuthResource.Loading -> {
                    Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT).show()
                }
                is AuthResource.Success -> {
                    Log.d("IMPORTANT FROM FRAGMENT", "No llega hasta el Success")
                    viewModel.logged(requireView())
                }
            }
        }
    }



    private fun checkIfUserNotEmpty() : Boolean {
        return binding.etEmail.text.isNotEmpty()
    }

    private fun checkIfPasswordNotEmpty() : Boolean {
        return binding.etPassword.text.isNotEmpty()
    }


}