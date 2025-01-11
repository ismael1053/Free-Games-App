package com.roque.free_games_app.data.remote.dto

import com.google.gson.annotations.SerializedName

data class DetailedGameDTO(

    @SerializedName("id")
    val id: Int,

    @SerializedName("title")
    val title: String,

    @SerializedName("release_date")
    val releaseDate: String,

    @SerializedName("publisher")
    val publisher: String,

    @SerializedName("developer")
    val developer: String,

    @SerializedName("genre")
    val genre: String,

    @SerializedName("platform")
    val platform: String,

    @SerializedName("short_description")
    val description: String,

    @SerializedName("description")
    val detailedDescription: String,

    @SerializedName("game_url")
    val gameUrl: String,

    @SerializedName("freetogame_profile_url")
    val freeToGameUrl: String,

    @SerializedName("thumbnail")
    val thumbnail: String,

    @SerializedName("minimum_system_requirements")
    val systemRequirements: SystemRequirementsDTO?,

    @SerializedName("screenshots")
    val screenshots: List<ScreenshotDTO>?
)

data class SystemRequirementsDTO(

    @SerializedName("os")
    val os: String,

    @SerializedName("processor")
    val processor: String,

    @SerializedName("memory")
    val memory: String,

    @SerializedName("graphics")
    val graphics: String,

    @SerializedName("storage")
    val storage: String,
)

data class ScreenshotDTO(

    @SerializedName("id")
    val id: Int,

    @SerializedName("image")
    val image: String,
)