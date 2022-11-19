package com.juanmi.gamertool.ui.register

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import com.juanmi.gamertool.core.AuthResource
import com.juanmi.gamertool.databinding.RegisterFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {
    private var _binding: RegisterFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = RegisterFragmentBinding.inflate(inflater, container, false)

        binding.btnRegister.setOnClickListener {
            if(checkIfUserNotEmpty() && checkIfPasswordNotEmpty() && checkIfRepasswordNotEmpty()) {
                viewModel.signupUser(binding.etName.text.toString(), binding.etEmail.text.toString(), binding.etPassword.text.toString())
            } else {
                Toast.makeText(requireContext(), "Not empty fields, please", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.signupFlow.asLiveData().observe(viewLifecycleOwner) {
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

        binding.leftBtn.setOnClickListener {
            viewModel.toLoginFragment(requireView())
        }

        return binding.root
    }

    private fun checkIfUserNotEmpty() : Boolean {
        return binding.etEmail.text.isNotEmpty()
    }

    private fun checkIfPasswordNotEmpty() : Boolean {
        return binding.etPassword.text.isNotEmpty()
    }

    private fun checkIfRepasswordNotEmpty() : Boolean {
        return binding.etRepassword.text.isNotEmpty()
    }


}