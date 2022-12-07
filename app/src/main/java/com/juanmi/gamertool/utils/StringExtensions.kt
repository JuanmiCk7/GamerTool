package com.juanmi.gamertool.utils

const val T_THUMB_STRING = "t_thumb"
const val T_SCREENSHOT_MED_STRING = "t_screenshot_med"
const val T_SCREENSHOT_BIG_STRING = "t_screenshot_big"
const val T_COVER_BIG_STRING = "t_cover_big"

/***
 * Métodos utilizados para formatear la URL de las capturas de pantalla.
 */
fun String?.formatScreenshotImageUrl(): String {
    return if (this.isNullOrEmpty()) {
        ""
    } else {
        formatUrl(this).replace(T_THUMB_STRING, T_SCREENSHOT_MED_STRING)
    }
}

fun String?.formatScreenshotBackgroundImageUrl(): String {
    return if (this.isNullOrEmpty()) {
        ""
    } else {
        formatUrl(this).replace(T_THUMB_STRING, T_SCREENSHOT_BIG_STRING)
    }
}

fun String?.formatCoverImageUrl(): String {
    return if (this.isNullOrEmpty()) {
        ""
    } else {
        formatUrl(this).replace(T_THUMB_STRING, T_COVER_BIG_STRING)
    }
}

fun formatUrl(url: String): String {
    return if (url.length > 6) {
        "https://" + url.subSequence(2, url.length).toString()
    } else {
        ""
    }
}