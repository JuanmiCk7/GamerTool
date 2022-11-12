package com.juanmi.gamertool.repository.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class GameScreenshot(
    val id: Int = 0,
    val url: String = "",
) : Parcelable