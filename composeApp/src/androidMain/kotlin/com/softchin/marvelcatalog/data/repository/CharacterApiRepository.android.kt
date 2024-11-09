package com.softchin.marvelcatalog.data.repository

import com.softchin.marvelcatalog.core.MARVEL_BASE_URL
import com.softchin.marvelcatalog.data.network.CharacterApiModel
import com.softchin.marvelcatalog.data.network.CharacterResult
import com.softchin.marvelcatalog.data.network.httpClient
import com.softchin.marvelcatalog.domain.model.Character
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.http.HttpHeaders
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

actual class CharacterApiRepository(
    private val clientHttp: HttpClient = httpClient(),
) : CharacterNetworkRepository {

    actual override suspend fun fetchCharacters(
        timestamp: Long,
        md5: String,
    ): Flow<List<Character>> =
        flow {
            emit(
                clientHttp.get("$MARVEL_BASE_URL/v1/public/characters") {
                    headers {
                        append(HttpHeaders.Accept, "application/json")
                    }
                    url.parameters["ts"] = "$timestamp"
                    url.parameters["hash"] = md5
                    url.parameters["limit"] = 80.toString()
                }.body<CharacterApiModel>().data?.results!!
                    .map { it.toCharacter() },
            )
        }

    actual override suspend fun fetchNewCharacters(
        timestamp: Long,
        md5: String,
        nameStartsWith: Char
    ): Flow<List<Character>> {
        return flow {
            emit(
                clientHttp.get("$MARVEL_BASE_URL/v1/public/characters") {
                    headers {
                        append(HttpHeaders.Accept, "application/json")
                    }
                    url.parameters["ts"] = "$timestamp"
                    url.parameters["hash"] = md5
                    url.parameters["limit"] = 80.toString()
                    url.parameters["nameStartsWith"] = nameStartsWith.toString()
                }.body<CharacterApiModel>().data?.results!!
                    .map { it.toCharacter() },
            )
        }
    }

    actual override suspend fun fetchCharacterBy(
        name: String, timestamp: Long, md5: String
    ): Flow<List<Character>> {
        return flow {
            emit(
                clientHttp.get("$MARVEL_BASE_URL/v1/public/characters") {
                    headers {
                        append(HttpHeaders.Accept, "application/json")
                    }

                    url.parameters["ts"] = "$timestamp"
                    url.parameters["hash"] = md5
                    url.parameters["nameStartsWith"] = name

                }.body<CharacterApiModel>().data?.results!!.map { it.toCharacter() }
            )
        }
    }

    private fun CharacterResult.toCharacter(): Character =
        Character(
            id = this.id,
            name = this.name,
            description = this.description,
            thumbnailUrl = this.thumbnail.toUrl(),
        )
}
