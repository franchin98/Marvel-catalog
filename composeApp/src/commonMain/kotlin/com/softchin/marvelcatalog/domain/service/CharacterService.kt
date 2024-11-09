package com.softchin.marvelcatalog.domain.service

import com.softchin.marvelcatalog.domain.model.Character
import com.softchin.marvelcatalog.domain.usecases.GetCharacterUseCase
import com.softchin.marvelcatalog.domain.usecases.LoadNewCharactersUseCase
import com.softchin.marvelcatalog.domain.usecases.SearchCharacterUseCase
import kotlinx.coroutines.flow.Flow

expect class CharacterService : GetCharacterUseCase, SearchCharacterUseCase, LoadNewCharactersUseCase {
    override suspend fun getCharacters(): Flow<List<Character>>
    override suspend fun searchCharactersByName(name: String): Flow<List<Character>>
    override suspend fun loadNewCharacters(): Flow<List<Character>>
}