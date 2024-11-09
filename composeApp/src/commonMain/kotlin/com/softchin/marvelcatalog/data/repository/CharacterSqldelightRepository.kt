package com.softchin.marvelcatalog.data.repository

import com.softchin.marvelcatalog.domain.model.Character
import kotlinx.coroutines.flow.Flow

expect class CharacterSqldelightRepository : CharacterLocalRepository {
    override suspend fun selectCharacters(): Flow<List<Character>>

    override suspend fun insertCharacter(character: Character): Character

    override suspend fun deleteAll()

    override suspend fun loadNewCharacters(): Flow<List<Character>>
}