package com.overlaydemo.controls

import kotlin.text.Regex

class CommonMethods {

    companion object {

        fun replaceFontsFromHTML(htmlCodeString: String): String {

            val fontUrlRegex = Regex("url\\(\"([^\"]+\\.(otf|ttf))\"\\)")

            val newHtmlCodeString = fontUrlRegex.replace(htmlCodeString) {
                val fontUrl = it.groupValues[1]
                val fontName = fontUrl.substringBeforeLast(".")
                    .replace("-", "").lowercase()
                val extension = fontUrl.substringAfterLast(".")
                "url(\"file:///android_res/font/$fontName.$extension\")"
            }

            return newHtmlCodeString

        }
    }

}
