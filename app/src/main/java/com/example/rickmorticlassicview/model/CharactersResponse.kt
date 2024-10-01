package com.example.rickmorticlassicview.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

data class CharactersResponse(
    @SerializedName("results") val characters: List<Character>,
    @SerializedName("info") val pageInfo: PageInfo
)

@Keep
data class Character (
    val id: Long,
    val name: String,
    val status: Status,
    val species: Species,
    val type: String,
    val gender: Gender,
    val origin: Location,
    val location: Location,
    val image: String,
    val episode: List<String>,
    val url: String,
    val created: String
)

@Keep
enum class Gender {
    Female,
    Male,
    Unknown
}

@Keep
data class Location (
    val name: String,
    val url: String
)

@Keep
enum class Species {
    Alien,
    Human
}

@Keep
enum class Status {
    Alive,
    Dead,
    Unknown
}

@Keep
data class PageInfo(
    val count: Int,
    val pages: Int,
    val next: String?,
    val prev: String?
)
