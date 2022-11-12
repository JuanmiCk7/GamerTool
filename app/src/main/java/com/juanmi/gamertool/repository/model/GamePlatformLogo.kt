package com.juanmi.gamertool.repository.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class GamePlatformLogo(
    val id: Int = 0,
    val url: String = "",
) : Parcelable