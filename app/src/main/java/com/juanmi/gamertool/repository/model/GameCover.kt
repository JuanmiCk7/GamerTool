package com.juanmi.gamertool.repository.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/***
 * Clase que define la caratula de un juego.
 */
@Parcelize
class GameCover(
    val id: Int = 0,
    val url: String = "",
) : Parcelable