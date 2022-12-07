package com.juanmi.gamertool.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Clase que define el desarrollador de un juego
 */
@Parcelize
class GameCompany (
    val id: Int = 0,
    val name: String = ""
) : Parcelable