package com.softchin.marvelcatalog.data.repository

import com.softchin.marvelcatalog.domain.model.Character
import kotlinx.coroutines.flow.Flow

actual class CharacterSqldelightRepository : CharacterLocalRepository {

    actual override suspend fun selectCharacters(): Flow<List<Character>> {
        TODO("Not yet implemented")
    }
}