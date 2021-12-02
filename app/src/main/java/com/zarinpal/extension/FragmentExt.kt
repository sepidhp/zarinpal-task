package com.zarinpal.extension

import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

fun Fragment.getColorRes(colorId: Int) = ContextCompat.getColor(requireContext(), colorId)

fun Fragment.toast(text: String, duration: Int = Toast.LENGTH_SHORT) =
    Toast.makeText(requireContext(), text, duration)