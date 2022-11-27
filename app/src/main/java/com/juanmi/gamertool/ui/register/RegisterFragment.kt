package com.juanmi.gamertool.ui.register

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import com.juanmi.gamertool.application.AuthResource
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
            hideKeyboard()
            if(checkIfNoFieldsEmpty()) {
                if(checkIfPasswordsMatch()) {
                    viewModel.signupUser(
                        binding.etName.text.toString(),
                        binding.etEmail.text.toString(),
                        binding.etPassword.text.toString()
                    )
                } else {
                    Toast.makeText(requireContext(), "The password must be the same", Toast.LENGTH_SHORT).show()
                }
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
                    hideKeyboard()
                    viewModel.logged(requireView())
                }
            }
        }

        binding.leftBtn.setOnClickListener {
            hideKeyboard()
            viewModel.toLoginFragment(requireView())
        }

        return binding.root
    }

    private fun Fragment.hideKeyboard() {
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().windowToken, 0)
    }

    private fun checkIfNoFieldsEmpty() : Boolean {
        return binding.etEmail.text.isNotEmpty() && binding.etPassword.text.isNotEmpty() && binding.etRepassword.text.isNotEmpty()
    }

    private fun checkIfPasswordsMatch(): Boolean {
        return binding.etPassword.text.toString() == binding.etRepassword.text.toString()
    }
}