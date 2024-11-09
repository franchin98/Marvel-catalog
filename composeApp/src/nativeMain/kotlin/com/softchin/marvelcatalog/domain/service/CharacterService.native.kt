package com.softchin.marvelcatalog.domain.service

import com.softchin.marvelcatalog.domain.model.Character
import com.softchin.marvelcatalog.domain.usecases.GetCharacterUseCase
import com.softchin.marvelcatalog.domain.usecases.SearchCharacterUseCase
import kotlinx.coroutines.flow.Flow

actual class CharacterService : GetCharacterUseCase, SearchCharacterUseCase {
    actual override suspend fun getCharacters(): Flow<List<Character>> {
        TODO("Not yet implemented")
    }

    actual override suspend fun searchCharactersByName(name: String): Flow<List<Character>> {
        TODO("Not yet implemented")
    }

}