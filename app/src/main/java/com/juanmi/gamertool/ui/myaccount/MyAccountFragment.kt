package com.juanmi.gamertool.ui.myaccount

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.juanmi.gamertool.databinding.MyAccountFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyAccountFragment: Fragment() {
    private var _binding: MyAccountFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MyAccountViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MyAccountFragmentBinding.inflate(inflater, container, false)

        binding.logoutBtn.setOnClickListener {
            viewModel.logout(requireView())
        }

        binding.fullnameTvHolder.text = viewModel.getCurrentUser()?.displayName
        binding.emailTvHolder.text = viewModel.getCurrentUser()?.email

        return binding.root
    }
}