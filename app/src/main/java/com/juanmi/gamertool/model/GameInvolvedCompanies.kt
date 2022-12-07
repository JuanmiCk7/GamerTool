package com.juanmi.gamertool.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * Clase que define una empresa involucrada en el desarrollo de un juego.
 */
@Parcelize
class GameInvolvedCompanies(
    val id: Int = 0,
    @SerializedName("company") val gameCompany: GameCompany? = GameCompany()
) : Parcelable