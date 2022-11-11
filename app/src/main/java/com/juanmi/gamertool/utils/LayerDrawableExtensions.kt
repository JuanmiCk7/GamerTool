package com.juanmi.gamertool.utils

import android.graphics.PorterDuff
import android.graphics.drawable.LayerDrawable

fun LayerDrawable.setStarsProgressColor(
    primaryStarColor: Int,
    secondaryStarColor: Int
) = this.apply {

    getDrawable(2).setColorFilter(
        primaryStarColor,
        PorterDuff.Mode.SRC_ATOP
    )
    getDrawable(0).setColorFilter(
        secondaryStarColor,
        PorterDuff.Mode.SRC_ATOP
    )
    getDrawable(1).setColorFilter(
        primaryStarColor,
        PorterDuff.Mode.SRC_ATOP
    )
}