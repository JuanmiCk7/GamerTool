package com.juanmi.gamertool.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class GameCompany (
    val id: Int = 0,
    val name: String = ""
) : Parcelable