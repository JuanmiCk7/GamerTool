package com.juanmi.gamertool.repository.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/***
 * Clase que define el género de un juego
 */
@Parcelize
class GameGenre(
    val id: Int = 0,
    val name: String = "",
) : Parcelable