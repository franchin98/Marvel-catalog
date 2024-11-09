package com.softchin.marvelcatalog.data.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CharacterApiModel(
    @SerialName("data") val data: CharacterData?,
)

@Serializable
data class CharacterData(
    @SerialName("results") val results: List<CharacterResult>,
)

@Serializable
data class CharacterResult(
    @SerialName("id") val id: Long,
    @SerialName("name") val name: String,
    @SerialName("description") val description: String,
    @SerialName("thumbnail") val thumbnail: Thumbnail,
)

@Serializable
data class Thumbnail(
    @SerialName("path") val path: String,
    @SerialName("extension") val extension: String,
) {
    fun toUrl(): String {
        if (path.contains("http:")) {
            return "${path.replace("http:", "https:")}.$extension"
        }

        return "$path.$extension"
    }
}
