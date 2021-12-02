package com.zarinpal.utils

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import javax.inject.Inject

class KeyboardManager @Inject constructor(context: Context) {

    private val inputMethodManager =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

    fun hideKeyboard(fragment: Fragment) {
        inputMethodManager.hideSoftInputFromWindow(fragment.view?.rootView?.windowToken, 0)
    }

    fun showKeyboard() {
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0)
    }
}