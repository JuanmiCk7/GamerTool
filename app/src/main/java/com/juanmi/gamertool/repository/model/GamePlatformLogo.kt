package com.juanmi.gamertool.repository.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/***
 * Clase que define el logotipo de la plataforma de un juego.
 */
@Parcelize
class GamePlatformLogo(
    val id: Int = 0,
    val url: String = "",
) : Parcelable