package com.juanmi.gamertool.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
class GameInvolvedCompanies(
    val id: Int = 0,
    @SerializedName("company") val gameCompany: GameCompany? = GameCompany()
) : Parcelable