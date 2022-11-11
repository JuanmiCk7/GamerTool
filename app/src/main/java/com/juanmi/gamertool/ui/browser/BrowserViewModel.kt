package com.juanmi.gamertool.ui.browser

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController

class BrowserViewModel: ViewModel() {

    fun onSearchClicked(gameName: String, view: View) {
        val action = BrowserFragmentDirections.actionBrowserFragmentToSearchFragment(gameName)
        view.findNavController().navigate(action)
    }

}