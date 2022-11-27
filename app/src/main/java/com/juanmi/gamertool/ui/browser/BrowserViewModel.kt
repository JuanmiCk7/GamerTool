package com.juanmi.gamertool.ui.browser

import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController

class BrowserViewModel: ViewModel() {
    fun onSearchClicked(gameName: String, view: View) {
        if(gameName.isNotBlank() || gameName.isNotEmpty()) {
            val action = BrowserFragmentDirections.actionBrowserFragmentToSearchFragment(gameName)
            view.findNavController().navigate(action)
        } else {
            Toast.makeText(view.context, "You must insert a game name!", Toast.LENGTH_SHORT).show()
        }
    }

}