package com.zarinpal.data.server

import android.app.Activity
import com.zarinpal.R
import com.zarinpal.utils.CustomDialog
import com.zarinpal.utils.dialog
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class ConnectionManager constructor(private val builder: ConnectionManagerBuilder) {

    fun show() {

        var icon: Int? = null
        val title: String
        var message: String? = null
        var showRetryButton = false

        if (builder.exception is UnknownHostException || builder.exception is SocketException) {
            icon = R.drawable.ic_wifi_off
            title = "خطای اتصال"
            message = "اتصال به اینترنت برقرار نیست."
            showRetryButton = true
        } else if (builder.exception is SocketTimeoutException) {
            icon = R.drawable.ic_wifi_off
            title = "خطای اتصال"
            message = "اتصال به سرور ممکن نیست."
            showRetryButton = true
        } else if (builder.exception is CallException) {

            title = "خطا (کد ${builder.exception.responseCode})"

            if (builder.exception.responseCode in 400 until 500) { // client error
                message = "خطایی در سمت کلاینت رخ داده است." // logical error or other client errors
                showRetryButton = false
            } else if (builder.exception.responseCode in 500 until 600) { // server error
                message = "خطایی در سرور رخ داده است."
                showRetryButton = true
            }
        } else { // runtime exceptions
            title = "خطای ناشناخته"
            message = "خطایی ناشناخته رخ داده است."
            showRetryButton = true
        }

        dialog(builder.activity) {

            cancelable = false
            this.icon = icon
            this.title = title
            this.message = message
            if (showRetryButton)
                positiveButton("تلاش مجدد") {
                    builder.onRetryClickedListener?.onButtonClicked(it)
                }
            negativeButton("بازگشت") {
                builder.onExitClickedListener?.onButtonClicked(it)
            }
        }.show()

    }

    fun interface DialogCallback {
        fun onButtonClicked(dialog: CustomDialog)
    }
}

class ConnectionManagerBuilder(
    val activity: Activity,
    val exception: Throwable,
    var onRetryClickedListener: ConnectionManager.DialogCallback? = null,
    var onExitClickedListener: ConnectionManager.DialogCallback? = null
) {

    fun onRetryClicked(listener: ConnectionManager.DialogCallback) {
        onRetryClickedListener = listener
    }

    fun onExitClicked(listener: ConnectionManager.DialogCallback) {
        onExitClickedListener = listener
    }

    fun build() = ConnectionManager(this)
}

fun apiDialog(
    activity: Activity,
    exception: Throwable,
    block: ConnectionManagerBuilder.() -> Unit
) = ConnectionManagerBuilder(activity, exception).apply(block).build()