package com.softchin.marvelcatalog.domain.service

import com.softchin.marvelcatalog.BuildConfig
import com.softchin.marvelcatalog.core.PUBLIC_KEY
import com.softchin.marvelcatalog.data.repository.CharacterLocalRepository
import com.softchin.marvelcatalog.data.repository.CharacterNetworkRepository
import com.softchin.marvelcatalog.domain.model.Character
import com.softchin.marvelcatalog.domain.usecases.GetCharacterUseCase
import com.softchin.marvelcatalog.domain.usecases.LoadNewCharactersUseCase
import com.softchin.marvelcatalog.domain.usecases.SearchCharacterUseCase
import com.softchin.marvelcatalog.utils.md5
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

actual class CharacterService(
    private val characterLocalRepo: CharacterLocalRepository,
    private val characterNetworkRepo: CharacterNetworkRepository
) : GetCharacterUseCase, SearchCharacterUseCase, LoadNewCharactersUseCase {

    actual override suspend fun getCharacters(): Flow<List<Character>> {
        return flow {
            emit(
                sort(
                    characterLocalRepo
                        .selectCharacters()
                        .first()
                )
            )
        }
    }

    actual override suspend fun searchCharactersByName(name: String): Flow<List<Character>> {
        val timeStamp = System.currentTimeMillis()
        return try {
            flow {
                emit(
                    sort(
                        characterNetworkRepo.fetchCharacterBy(
                            name,
                            timestamp = timeStamp,
                            md5 = md5(timeStamp.toString() + BuildConfig.PRIVATE_KEY + PUBLIC_KEY)
                        ).first()
                            .filter { character -> !character.thumbnailUrl.contains("not_available") }
                    )
                )
            }
        } catch (ex: Exception) {
            flow {
                emit(
                    characterLocalRepo.selectCharacters().first()
                        .filter { it.name.startsWith(name[0]) })
            }
        }
    }

    actual override suspend fun loadNewCharacters(): Flow<List<Character>> {
        characterLocalRepo.deleteAll()
        return flow {
            emit(
                characterLocalRepo.loadNewCharacters().first()
            )
        }
    }

    private fun sort(characters: List<Character>): List<Character> {
        return characters.sortedWith(CharacterComparator())
    }

    private class CharacterComparator : Comparator<Character> {
        override fun compare(c1: Character, c2: Character): Int {
            if (c1.description.isEmpty() && c2.description.isEmpty()) {
                return c2.id.compareTo(c1.id)
            }
            if (c1.description.isNotEmpty() && c2.description.isNotEmpty()) {
                return c1.id.compareTo(c2.id)
            }
            if (c1.description.isNotEmpty() && c2.description.isEmpty()) {
                return -1
            }
            return 1
        }
    }

}
