package com.overlaydemo.controls

import android.content.Context
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import com.overlaydemo.R
import java.io.IOException

class CustomWebViewClient(private val context: Context) : WebViewClient() {

    override fun shouldInterceptRequest(view: WebView, request: WebResourceRequest): WebResourceResponse? {
        val url = request.url.toString()
        if (url.endsWith(".ttf") || url.endsWith(".otf")) {
            try {
                val fontInputStream = context.resources.openRawResource(R.font.ooredooheavy)
                return WebResourceResponse("font/ttf", "UTF-8", fontInputStream)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return super.shouldInterceptRequest(view, request)
    }
}