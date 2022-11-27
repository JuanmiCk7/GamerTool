package com.juanmi.gamertool.ui.browser

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.juanmi.gamertool.databinding.BrowserFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BrowserFragment : Fragment() {

    private var _binding: BrowserFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: BrowserViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BrowserFragmentBinding.inflate(inflater, container, false)

        binding.buttonSearchGame.setOnClickListener {
            hideKeyboard()
            viewModel.onSearchClicked(binding.editTextGameName.text.toString(), requireView())
        }

        return binding.root
    }

    /***
     * Metodo que oculta el teclado
     */
    private fun Fragment.hideKeyboard() {
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().windowToken, 0)
    }
}