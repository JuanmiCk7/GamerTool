package com.juanmi.gamertool.repository.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class GameGenre(
    val id: Int = 0,
    val name: String = "",
) : Parcelable