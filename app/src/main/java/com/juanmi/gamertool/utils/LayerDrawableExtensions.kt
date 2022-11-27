package com.juanmi.gamertool.utils

import android.graphics.drawable.LayerDrawable
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat

fun LayerDrawable.setStarsProgressColor(
    primaryStarColor: Int,
    secondaryStarColor: Int
) = this.apply {

    getDrawable(2).colorFilter = BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
        primaryStarColor,
        BlendModeCompat.SRC_ATOP
    )

    getDrawable(0).colorFilter = BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
        secondaryStarColor,
        BlendModeCompat.SRC_ATOP
    )

    getDrawable(1).colorFilter = BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
        primaryStarColor,
        BlendModeCompat.SRC_ATOP
    )
}