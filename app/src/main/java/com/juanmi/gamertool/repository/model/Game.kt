package com.juanmi.gamertool.repository.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.text.SimpleDateFormat
import java.util.*

/***
 * Clase que define un juego.
 */
@Parcelize
data class Game(
    val id: Int = 0,
    val cover: GameCover? = GameCover(-1, ""),
    val genres: List<GameGenre>? = listOf(),
    val name: String? = "",
    val platforms: List<GamePlatform>? = listOf(),
    val storyline: String? = "",
    val summary: String? = "",
    val url: String? = "",
    @SerializedName("first_release_date") val releaseDate: Long? = 0,
    @SerializedName("rating") val rating: Double? = 0.0,
    @SerializedName("total_rating") val totalRating: Double? = 0.0,
    val ratingCount: Int? = 0,
    val screenshots: List<GameScreenshot>? = listOf(),
    val complete: Boolean = false,
    val comesFromFirestore: Boolean = false
) : Parcelable

/***
 * Método utilizado para obtener el género de un juego
 */
fun Game.getGenres() : String {
    var genresString = ""
    if (!this.genres.isNullOrEmpty()) {
        genresString = this.genres[0].name
        if (this.genres.size > 1) {
            for (i in 1 until this.genres.size) {
                genresString += ", ${this.genres[i].name}"
            }
        }
    }
    return genresString
}

/***
 * Método utilizado para obtener la fecha de lanzamiento de un juego.
 */
fun Game.getReleaseDate(): String {
    val sfd = SimpleDateFormat("dd/MM/yyyy", Locale.GERMANY)
    val netDate = Date(this.releaseDate?.times(1000)!!.toLong())
    return sfd.format(netDate).toString()
}