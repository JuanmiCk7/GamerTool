package com.juanmi.gamertool.ui.login

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
import com.juanmi.gamertool.databinding.LoginFragmentBinding
import dagger.hilt.android.AndroidEntryPoint


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
                    hideKeyboard()
                    viewModel.logged(requireView())
                }
            }
        }
    }

    private fun Fragment.hideKeyboard() {
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().windowToken, 0)
    }

    private fun checkIfUserNotEmpty() : Boolean {
        return binding.etEmail.text.isNotEmpty()
    }

    private fun checkIfPasswordNotEmpty() : Boolean {
        return binding.etPassword.text.isNotEmpty()
    }


}