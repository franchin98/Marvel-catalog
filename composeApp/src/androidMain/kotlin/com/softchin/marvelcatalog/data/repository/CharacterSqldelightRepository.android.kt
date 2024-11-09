package com.softchin.marvelcatalog.data.repository

import android.content.Context
import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.softchin.marvel.database.Marvel
import com.softchin.marvelcatalog.BuildConfig
import com.softchin.marvelcatalog.core.PUBLIC_KEY
import com.softchin.marvelcatalog.data.local.DriverFactory
import com.softchin.marvelcatalog.domain.model.Character
import com.softchin.marvelcatalog.utils.md5
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

private val LETTERS = listOf(
    'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o',
    'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
)

actual class CharacterSqldelightRepository(
    context: Context,
    private val characterNetworkRepository: CharacterNetworkRepository
) : CharacterLocalRepository {
    private val privateKey = BuildConfig.PRIVATE_KEY
    private val androidSqliteDriver = DriverFactory(context).createDataBaseDriver()
    private val marvelDatabase = Marvel(androidSqliteDriver)
    private val characters = marvelDatabase.characterQueries
        .selectAllCharacters()
        .asFlow()
        .mapToList(Dispatchers.IO)

    actual override suspend fun selectCharacters(): Flow<List<Character>> {

        if (characters.first().isEmpty()) {
            val timestamp = System.currentTimeMillis()
            val charactersFromApi: List<Character> =
                characterNetworkRepository.fetchCharacters(
                    timestamp,
                    md5(timestamp.toString() + privateKey + PUBLIC_KEY)
                ).first()

            charactersFromApi.forEach { character ->
                if (!character.thumbnailUrl.contains("not_available"))
                    marvelDatabase.characterQueries.insertCharacter(
                        character.id,
                        character.name,
                        character.description,
                        character.thumbnailUrl,
                    )
            }
            return flow {
                emit(characters.first().map { it.toCharacter() })
            }
        }

        return flow {
            emit(characters.first().map { it.toCharacter() })
        }
    }

    actual override suspend fun insertCharacter(character: Character): Character {
        val charactersAux = characters.first().map { it.toCharacter() }

        if (!charactersAux.contains(character)) {
            marvelDatabase.characterQueries.insertCharacter(
                id_character = character.id,
                name = character.name,
                description = character.description,
                image_url = character.thumbnailUrl,
            )
        }

        return character
    }

    actual override suspend fun deleteAll() {
        marvelDatabase.characterQueries.deleteAll()
    }

    actual override suspend fun loadNewCharacters(): Flow<List<Character>> {
        val randomLetter = LETTERS.random()
        val timestamp = System.currentTimeMillis()
        val newCharacters = characterNetworkRepository.fetchNewCharacters(
            timestamp, md5(timestamp.toString() + privateKey + PUBLIC_KEY), randomLetter
        )

        newCharacters.first().forEach { character ->
            if (!character.thumbnailUrl.contains("not_available"))
                marvelDatabase.characterQueries.insertCharacter(
                    character.id,
                    character.name,
                    character.description,
                    character.thumbnailUrl,
                )
        }

        return flow {
            emit(
                characters.first().map { it.toCharacter() }
            )
        }

    }
}

private fun com.softchin.marvel.database.Character.toCharacter() = Character(
    id = this.id_character,
    name = this.name,
    description = this.description.orEmpty(),
    thumbnailUrl = this.image_url.orEmpty()
)
