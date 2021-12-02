package com.zarinpal.utils

import android.app.Activity
import android.app.Dialog
import com.zarinpal.R
import com.zarinpal.databinding.DialogCircularProgressBinding

class ProgressDialog(activity: Activity, Cancelable: Boolean) : Dialog(activity, R.style.TransparentDialogTheme) {

    init {
        setContentView(DialogCircularProgressBinding.inflate(layoutInflater).root)
        setCancelable(Cancelable)
        setCanceledOnTouchOutside(false)
    }
}