package com.juanmi.gamertool.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/***
 * Clase que define el captura de un juego.
 */
@Parcelize
class GameScreenshot(
    val id: Int = 0,
    val url: String = "",
) : Parcelable