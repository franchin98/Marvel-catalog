package com.softchin.marvelcatalog.data.repository

import com.softchin.marvelcatalog.domain.model.Character
import kotlinx.coroutines.flow.Flow

interface CharacterLocalRepository {
    suspend fun selectCharacters(): Flow<List<Character>>

    suspend fun insertCharacter(character: Character): Character

    suspend fun deleteAll()

    suspend fun loadNewCharacters(): Flow<List<Character>>

}