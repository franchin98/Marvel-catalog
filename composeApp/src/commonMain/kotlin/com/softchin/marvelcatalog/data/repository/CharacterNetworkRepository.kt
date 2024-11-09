package com.softchin.marvelcatalog.data.repository

import com.softchin.marvelcatalog.domain.model.Character
import kotlinx.coroutines.flow.Flow

interface CharacterNetworkRepository {
    suspend fun fetchCharacters(timestamp: Long, md5: String): Flow<List<Character>>
    suspend fun fetchNewCharacters(timestamp: Long, md5: String, nameStartsWith: Char): Flow<List<Character>>
    suspend fun fetchCharacterBy(name: String, timestamp: Long, md5: String): Flow<List<Character>>
}