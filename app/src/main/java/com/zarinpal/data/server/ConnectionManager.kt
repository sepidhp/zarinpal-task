package com.zarinpal.data.server

import android.app.Activity
import com.apollographql.apollo.exception.ApolloNetworkException
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

        if (builder.exception is ApolloNetworkException || builder.exception is SocketException) {
            icon = R.drawable.ic_wifi_off
            title = getString(R.string.connection_error)
            message = getString(R.string.no_internet)
            showRetryButton = true
        } else if (builder.exception is SocketTimeoutException) {
            icon = R.drawable.ic_wifi_off
            title = getString(R.string.connection_error)
            message = getString(R.string.socket_timeout)
            showRetryButton = true
        } else if (builder.exception is CallException) {

            title = getString(R.string.error_with_code, builder.exception.responseCode)

            if (builder.exception.responseCode in 400 until 500) { // client error
                message =
                    getString(R.string.client_error_occurred) // logical error or other client errors
                showRetryButton = false
            } else if (builder.exception.responseCode in 500 until 600) { // server error
                message = getString(R.string.server_error_occurred)
                showRetryButton = true
            }
        } else { // runtime exceptions
            title = getString(R.string.unknown_error)
            message = getString(R.string.unknown_error_occurred)
            showRetryButton = true
        }

        dialog(builder.activity) {

            cancelable = false
            this.icon = icon
            this.title = title
            this.message = message
            if (showRetryButton)
                positiveButton(getString(R.string.retry)) {
                    builder.onRetryClickedListener?.onButtonClicked(it)
                }
            negativeButton(getString(R.string.return_)) {
                builder.onExitClickedListener?.onButtonClicked(it)
            }
        }.show()
    }

    private fun getString(id: Int) = builder.activity.resources.getString(id)

    private fun getString(id: Int, param: Int) =
        builder.activity.resources.getString(id, param)

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