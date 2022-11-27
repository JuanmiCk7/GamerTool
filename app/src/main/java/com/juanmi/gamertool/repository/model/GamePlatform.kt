package com.juanmi.gamertool.repository.model

import android.os.Parcelable

import kotlinx.parcelize.Parcelize

/***
 * Clase que define la plataforma de un juego.
 */
@Parcelize
class GamePlatform(
    val id: Int = 0,
    val name: String = ""
) : Parcelable