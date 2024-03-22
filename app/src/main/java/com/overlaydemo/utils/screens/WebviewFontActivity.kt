package com.overlaydemo.utils.screens

import android.os.Bundle
import android.util.Log
import android.webkit.WebSettings
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.overlaydemo.controls.CommonMethods
import com.overlaydemo.databinding.ActivityWebviewFontBinding

class WebviewFontActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWebviewFontBinding
    private val htmlCodeString = "<!DOCTYPE html>\n<html>\n   <head>\n      <meta charset=\"UTF-8\">\n      <title>IM3 Package</title>\n      <style type=\"text/css\"> @font-face{font-family: Ooredoo; src: url(\"ooredo-o-Heavy.otf\") format(\"opentype\")}@font-face{font-family: NotoSans; src: url(\"rubikpuddlesregular.otf\") format(\"truetype\")} @font-face{font-family: Ooredoo; src: url(\"rubikpuddlesregular.otf\") format(\"opentype\")}body, div, html, p, span{margin: 0; padding: 0; border: 0; font: inherit; vertical-align: baseline}body{font-family: NotoSans !important; font-size: 14px; font-weight: 400; line-height: 1; padding: 1em}.container{display: grid; grid-template-columns: repeat(12, 1fr)}.col-12, .col-6{margin: 10px 0}p, ul, li{font-family: 'Noto Sans', sans-serif; line-height: 18px;}.bold, .title{font-family: Ooredoo; margin: 15px 0; font-weight: 700}.col-12{grid-column: span 12}.title{font-size: 1em; color: rgba(114, 114, 114, 1)}.title2{font-size: 1em; color: rgb(212, 25, 25)}.bold{font-size: 2em; color: #000}</style>\n   </head>\n   <body>\n      <div class=\"container\">\n         <div class=\"col-12\">\n            <div class=\"title\">MAIN QUOTA</div>\n            <div class=\"bold\">5 GB</div>\n            <p>5 Days</p>\n            <ul>\n               <li>100% Main Quota</li>\n               <li>Auto renewal with 5 Days Active Period</li>\n            </ul>\n         </div>\n         <br>\n         <div class=\"col-12\" style=\"display: flex;flex-direction: row;justify-content: space-between;align-items:center;border-top:1px solid #f3f3f3;border-bottom: 1px solid #f3f3f3;padding:14px 0;\"> <a href=\"https://indosatooredoo.com/portal/id/psnewpulsasafe\" ) target=\"_blank\" style=\"cursor:pointer;\"> <img src=\"https://myim3banner.kloc.co/assets/uploads/pulsa_safe_1567051259.png\" alt=\"Pulsa Safe\" style=\"width:50%;height:auto;\"> <img src=\"https://myim3banner.kloc.co/assets/uploads/info_1577415332.png\" style=\"width:6%;height:auto;padding-left: 40%;\" alt=\"info\"> </a> </div>\n         <br>\n      </div>\n   </body>\n</html>"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflate = ActivityWebviewFontBinding.inflate(layoutInflater)
        binding = inflate ?: throw IllegalStateException("binding not found")
        setContentView(inflate.root)
        initViews()
    }

    private fun initViews() {
        val webSettings: WebSettings = binding.webview.settings
        webSettings.javaScriptEnabled = true
        val webViewClient = WebViewClient()
        binding.webview.webViewClient = webViewClient
        binding.webview.settings.javaScriptEnabled = true
        val replacedHtml = CommonMethods.replaceFontsFromHTML(htmlCodeString)
        Log.e("MApFont", replacedHtml)
        binding.webview.loadDataWithBaseURL(null, replacedHtml, "text/html", "UTF-8", null)
    }
}
