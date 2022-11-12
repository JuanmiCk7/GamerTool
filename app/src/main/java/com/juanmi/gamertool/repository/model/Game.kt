package com.juanmi.gamertool.repository.model


import android.os.Build
import android.os.Parcelable
import androidx.annotation.RequiresApi
import kotlinx.parcelize.Parcelize
import java.time.format.DateTimeFormatter

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
    val releaseDate: Long? = 0,
    val rating: Double? = 0.0,
    val totalRating: Double? = 0.0,
    val ratingCount: Int? = 0,
    val screenshots: List<GameScreenshot>? = listOf(),
    val complete: Boolean = false
) : Parcelable

fun Game.getPlatformsNames(): String {
    var stringResult = ""
    if (this.platforms != null && this.platforms.isNotEmpty()) {
        stringResult = this.platforms[0].name
        if (this.platforms.size > 1) {
            for (i in 1 until this.platforms.size) {
                stringResult += ", ${this.platforms[i].name}"
            }
        }
    }
    return stringResult
}

@RequiresApi(Build.VERSION_CODES.O)
fun Game.getReleaseDate(): String {
    val dateFromTimeStamp = DateTimeFormatter.ISO_INSTANT
        .format(java.time.Instant.ofEpochSecond(this.releaseDate ?: 0))
    return dateFromTimeStamp.subSequence(0, 4).toString()
}