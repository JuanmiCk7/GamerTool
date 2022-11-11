package com.juanmi.gamertool.utils

import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.google.android.material.snackbar.Snackbar
import com.juanmi.gamertool.R
import com.juanmi.gamertool.core.Event

fun Fragment.showSnackbar(text: String, duration: Int) {
    activity?.let {
        val snackbar = Snackbar.make(
            it.findViewById(R.id.container),
            text,
            duration
        )
        val view = snackbar.view
        view.setBackgroundColor(resources.getColor(R.color.snackbarColor))
        snackbar.show()
    }
}

fun Fragment.setupSnackbar(owner: LifecycleOwner, event: LiveData<Event<Int>>, duration: Int) {
    event.observe(owner) { event ->
        event.getContentIfNotHandled()?.let { res ->
            context?.let { showSnackbar(it.getString(res), duration) }
        }
    }
}