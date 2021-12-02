package com.zarinpal.utils

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.View
import android.webkit.WebChromeClient
import android.widget.FrameLayout

class CustomWebChromeClient(private val activity: Activity) : WebChromeClient() {

    private var mCustomView: View? = null
    private var mCustomViewCallback: CustomViewCallback? = null

    private var mOriginalOrientation = 0
    private var mOriginalSystemUiVisibility = 0

    override fun getDefaultVideoPoster(): Bitmap? {
        return if (mCustomView == null) {
            null
        } else BitmapFactory.decodeResource(activity.resources, 2130837573)
    }

    override fun onHideCustomView() {

        (activity.window.decorView as FrameLayout).removeView(mCustomView)
        mCustomView = null

        activity.window.decorView.systemUiVisibility =
            mOriginalSystemUiVisibility // TODO: 2021-11-14 covert to WindowInsetsController
        activity.requestedOrientation = mOriginalOrientation
        mCustomViewCallback?.onCustomViewHidden()
        mCustomViewCallback = null
    }

    override fun onShowCustomView(paramView: View, paramCustomViewCallback: CustomViewCallback) {
        if (mCustomView != null) {
            onHideCustomView()
            return
        }
        mCustomView = paramView
        mOriginalSystemUiVisibility = activity.window.decorView.systemUiVisibility
        mOriginalOrientation = activity.requestedOrientation
        mCustomViewCallback = paramCustomViewCallback
        (activity.window.decorView as FrameLayout).addView(
            mCustomView,
            FrameLayout.LayoutParams(-1, -1)
        )
        activity.window.decorView.systemUiVisibility = 3846 or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
    }
}